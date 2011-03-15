package de.atns.common.numbergenerator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.fileutils.FileUtil;

import java.io.*;

import static java.lang.Long.parseLong;


/**
 * @author tbaum
 * @since 06.11.2009
 */
@Singleton
public class NumberGenerator {
// ------------------------------ FIELDS ------------------------------

    private final File orderNumberDir;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public NumberGenerator(@NumberDir final String numberDir) {
        this.orderNumberDir = new File(numberDir);
        if (!this.orderNumberDir.exists()) {
            if (!this.orderNumberDir.mkdirs()) {
                throw new RuntimeException("unable to create counter-dir");
            }
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public long next(final NumberType type, final String subType, final Object... data) {
        synchronized (orderNumberDir) {
            File d1 = new File(orderNumberDir, type.name());
            if (subType != null && !subType.isEmpty()) {
                d1 = new File(d1, subType);
            }
            File[] f = d1.listFiles();
            if (f == null || f.length == 0) {
                final File newFile = new File(d1, "0");
                writeLine(newFile, "");
                f = new File[]{newFile};
            }
            if (f.length > 1) {
                throw new RuntimeException("more than one numbercounter in " + d1);
            }


            final long current;
            switch (type.period()) {
                case NONE:
                    current = parseLong(f[0].getName()) + 1;
                    break;
                case DAY:
                case MONTH:
                case YEAR:
                    final String content = readLine(f[0]);
                    final String prefix = type.period().currentPrefix(data);
                    if (!content.equals(prefix)) {
                        current = 1;
                        writeLine(f[0], prefix);
                    } else {
                        current = parseLong(f[0].getName()) + 1;
                    }
                    break;
                default:
                    throw new RuntimeException("typ " + type.period() + " nicht implementiert");
            }

            if (!f[0].renameTo(new File(d1, String.valueOf(current)))) {
                throw new RuntimeException("unable to create new ordernumber");
            }
            return current;
        }
    }

    private String readLine(final File file) throws RuntimeException {
        BufferedReader fr = null;
        try {
            fr = new BufferedReader(new FileReader(file));
            return fr.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            FileUtil.closeSilent(fr);
        }
    }

    private void writeLine(final File file, final String prefix) {
        try {
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();

            final PrintWriter f = new PrintWriter(file);
            f.println(prefix);
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
