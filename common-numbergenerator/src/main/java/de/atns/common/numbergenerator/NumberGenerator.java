package de.atns.common.numbergenerator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.File;
import java.io.IOException;


/**
 * @author tbaum
 * @since 06.11.2009
 */
@Singleton public class NumberGenerator {
// ------------------------------ FIELDS ------------------------------

    private final File orderNumberDir;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public NumberGenerator(@OrderNumberDir final String numberDir) {
        this.orderNumberDir = new File(numberDir);
        if (!this.orderNumberDir.exists()) {
            if (!this.orderNumberDir.mkdirs()) {
                throw new RuntimeException("unable to create counter-dir");
            }
        }
        if (this.orderNumberDir.listFiles().length == 0) {
            try {
                if (!new File(numberDir, "0").createNewFile()) {
                    throw new RuntimeException("unable to create counter");
                }
            } catch (IOException e) {
                throw new RuntimeException("unable to create counter");
            }
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public long next() {
        synchronized (orderNumberDir) {
            final File[] f = orderNumberDir.listFiles();
            if (f.length != 1) {
                throw new RuntimeException("more than one numbercounter in " + orderNumberDir);
            }

            final long current = Long.parseLong(f[0].getName()) + 1;

            if (!f[0].renameTo(new File(orderNumberDir, String.valueOf(current)))) {
                throw new RuntimeException("unable to create new ordernumber");
            }
            return current;
        }
    }
}
