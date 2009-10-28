package de.atns.shop.tray.printing;

import de.atns.shop.tray.Prefs;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;

public class FopUtil {
// ------------------------------ FIELDS ------------------------------

    public static final String osName = System.getProperty("os.name");

// -------------------------- STATIC METHODS --------------------------

    public static void convertFo2Awt(final String data, final String xsltSource) {
        final FopFactory fopFactory = fopFactory();
        final Fop fop;
        try {
            fop = fopFactory.newFop(MimeConstants.MIME_FOP_AWT_PREVIEW);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        transform(data, xsltSource, fop);
    }

    private static FopFactory fopFactory() {
        final FopFactory fopFactory = FopFactory.newInstance();
        fopFactory.setURIResolver(new SecurityManagerWorkaroundResolver());
        return fopFactory;
    }

    private static void transform(final String printable, final String xsltSource, final Fop fop) {
        final TransformerFactory fac = TransformerFactory.newInstance();
        fac.setURIResolver(new SecurityManagerWorkaroundResolver());
        try {
            final Transformer transformer1 = fac.newTransformer(new StreamSource(new URL(xsltSource).openStream()));
            transformer1.transform(new StreamSource(new StringReader(printable)), new SAXResult(fop.getDefaultHandler()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void print(final String data, final String xsltSource) {
        final FopFactory fopFactory = fopFactory();
        final Fop fop;
        try {
            fop = fopFactory.newFop(MimeConstants.MIME_FOP_PRINT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        transform(data, xsltSource, fop);
    }

    public static void save(final String elem, final String xsltSource, final String filename) {
        final JFileChooser chooser = new JFileChooser();
        final FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(final File file) {
                return file.getName().endsWith(".pdf");
            }

            @Override
            public String getDescription() {
                return "PDF-Files";
            }
        };

        String pdfDir = Prefs.getString(Prefs.PDF_DIR);
        if (pdfDir == null || !new File(pdfDir).isDirectory() || new File(pdfDir).exists()) {
            pdfDir = getDesktopFolder().getAbsolutePath();
        }


        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(false);
        chooser.setSelectedFile(new File(pdfDir, filename));
        chooser.setCurrentDirectory(new File(pdfDir));
        final JFrame f = new JFrame("TrayApp");
        f.setAlwaysOnTop(true);
        final int returnVal = chooser.showSaveDialog(f);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getPath());
            Prefs.set(Prefs.PDF_DIR, chooser.getSelectedFile().getParentFile().getAbsolutePath());
            safePdf(elem, xsltSource, chooser.getSelectedFile());
        }
        f.dispose();
    }

    public static void safePdf(final String data, final String xsltSource, final File file) {
        final FopFactory fopFactory = fopFactory();
        final Fop fop;
        final OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file);
            fop = fopFactory.newFop(MimeConstants.MIME_PDF, outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        transform(data, xsltSource, fop);
        try {
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static File getDesktopFolder() {
        if (osName.startsWith("Windows")) {
            return new File(System.getProperty("user.home"), "Desktop");
        }

        return new File("");
    }
}
