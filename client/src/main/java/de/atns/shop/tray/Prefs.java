package de.atns.shop.tray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public enum Prefs {
    //PORT("server.port", 18080),
    BANKING_PIN("banking.pin", null),
    BANKING_KTO("banking.kto", null),
    BANKING_LOGIN("banking.login", null),
    BANKING_BLZ("banking.blz", null),
    NAME("name", null),
    SERVER("server", null),
    BANKING_SERVER("banking_server", null),
    LABEL_PRINTER("label_drucker", null),
    LAUFZETTEL_PRINTER("laufzettel_drucker", null),
    LOCAL_LAUFZETTEL_PRINTER("local_laufzettel_drucker", null),
    LOCAL_LABEL_PRINTER("local_label_drucker", null),
    RECHNUNG_XSLT("rechnung_xslt", null),
    BESTELLUNG_XSLT("bestellung_xslt", null),
    PDF_DIR("pdf_dir", null);

// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(Prefs.class);
    private static final Preferences prefs = Preferences.userNodeForPackage(TrayApp.class);
    private static final String[] STRING_ARRAY0 = new String[]{};
    private int intValue;
    private final String key;
    private final String stringValue;
    private boolean boolValue;

// -------------------------- STATIC METHODS --------------------------

    public static String getString(final Prefs k) {
        return prefs.get(k.key, k.stringValue);
    }

    public static String getString(final Prefs k, final String c) {
        return getConfig(c).get(k.key, k.stringValue);
    }

    public static void set(final Prefs k, final String s) {
        if (s == null) {
            prefs.remove(k.key);
        } else {
            prefs.put(k.key, s);
        }
    }

    public static void set(final Prefs k, final Boolean s) {
        if (s == null) {
            prefs.remove(k.key);
        } else {
            prefs.putBoolean(k.key, s);
        }
    }

    public static void set(final Prefs k, final String c, final String s) {
        final Preferences p = getConfig(c);
        if (s == null) {
            p.remove(k.key);
        } else {
            p.put(k.key, s);
        }
    }

    public static Preferences getConfig(final String id) {
        return prefs.node(id);
    }

    public static void set(final Prefs k, final String id, final Boolean s) {
        final Preferences p = getConfig(id);
        if (s == null) {
            p.remove(k.key);
        } else {
            p.putBoolean(k.key, s);
        }
    }

    public static String[] getConfigNames() {
        try {
            return prefs.childrenNames();
        } catch (BackingStoreException e) {
            LOG.error(e.getMessage(), e);
        }
        return STRING_ARRAY0;
    }

    // --------------------------- CONSTRUCTORS ---------------------------

    private Prefs(final String s, final String s1) {
        this.key = s;
        this.stringValue = s1;
    }
}
