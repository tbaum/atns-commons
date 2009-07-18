package mareprint.web.server.payment;

import com.thoughtworks.xstream.XStream;

import java.io.*;

import mareprint.web.client.model.Auftrag;

/**
 * @author Michael Hunger
 * @since 18.07.2009
 */
public class AuftragStorage {
    public static Auftrag lade(final String path, final String rechnungsNummer) {
        final Reader file = createReader(path, rechnungsNummer);
        if (file == null) return null;
        return (Auftrag) new XStream().fromXML(file);
    }

    public void speichere(final String path, final Auftrag auftrag) {
        final FileWriter writer = createWriter(createFile(path, auftrag.getRechnungsNr()));
        new XStream().toXML(auftrag, writer);
    }

    private FileWriter createWriter(final File file) {
        try {
            return new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Reader createReader(final String path, final String rechnungsNummer) {
        try {
            return new BufferedReader(new FileReader(createFile(path, rechnungsNummer)));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private static File createFile(final String path, final String rechnungsNummer) {
        return new File(path, rechnungsNummer + ".xml");
    }
}
