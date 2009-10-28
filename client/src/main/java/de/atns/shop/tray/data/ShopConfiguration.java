package de.atns.shop.tray.data;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.RequestScoped;
import de.atns.shop.tray.AuthtokenCache;
import de.atns.shop.tray.Prefs;
import de.atns.shop.tray.PrinterTyp;
import de.atns.shop.tray.TrayApp;
import static de.atns.shop.tray.Util.extractParameter;
import de.atns.shop.tray.gui.ConfigurationDialog;

import java.util.Map;

@RequestScoped public class ShopConfiguration {
// ------------------------------ FIELDS ------------------------------

    private String name;
    private String pin;
    private String kto;
    private String login;
    private String blz;
    private boolean safePin;
    private String remotingHost;
    private String labelPrinterUrl;
    private String bankingServer;
    private String laufzettelPrinter;
    private String localLabelPrinter;
    private String localLaufzettelPrinter;
    private String rechnungXslt;
    private String bestellungXslt;
    private final String id;
    private String auth;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public ShopConfiguration(@RequestParameters final Map<String, String[]> params) {
        this(extractParameter(params, TrayApp.SHOP_ID));
        String auth;
        try {
            auth = extractParameter(params, TrayApp.AUTHTOKEN);
        } catch (Exception e) {
            auth = null;
        }
        this.auth = auth;
    }

    public ShopConfiguration(final String id) {
        this.id = id;
        loadConfiguration();
    }

    private void loadConfiguration() {
        loadOrFillConfiguration(id, new ConfigurationDialog() {
            @Override public boolean isDataComplete(final ShopConfiguration shopConfiguration) {
                return true;
            }

            @Override public boolean showDialog(final ShopConfiguration shopConfiguration) {
                return true;
            }
        });
    }

    private void loadOrFillConfiguration(final String id, final ConfigurationDialog dialog) {
        this.remotingHost = Prefs.getString(Prefs.SERVER, id);
        this.name = Prefs.getString(Prefs.NAME, id);
        this.pin = Prefs.getString(Prefs.BANKING_PIN, id);
        this.kto = Prefs.getString(Prefs.BANKING_KTO, id);
        this.login = Prefs.getString(Prefs.BANKING_LOGIN, id);
        this.blz = Prefs.getString(Prefs.BANKING_BLZ, id);
        this.bankingServer = Prefs.getString(Prefs.BANKING_SERVER, id);
        this.labelPrinterUrl = Prefs.getString(Prefs.LABEL_PRINTER, id);
        this.rechnungXslt = Prefs.getString(Prefs.RECHNUNG_XSLT, id);
        this.bestellungXslt = Prefs.getString(Prefs.BESTELLUNG_XSLT, id);
        this.laufzettelPrinter = Prefs.getString(Prefs.LAUFZETTEL_PRINTER, id);
        this.localLaufzettelPrinter = Prefs.getString(Prefs.LOCAL_LAUFZETTEL_PRINTER);
        this.localLabelPrinter = Prefs.getString(Prefs.LOCAL_LABEL_PRINTER);


        if (!dialog.isDataComplete(this)) {
            if (!dialog.showDialog(this)) {
                throw new RuntimeException("abbruch");
            }
            this.store(id);
        }
        // return configuration;
    }

    public void store(final String id) {
        Prefs.set(Prefs.BANKING_PIN, id, safePin ? pin : null);
        Prefs.set(Prefs.BANKING_KTO, id, kto);
        Prefs.set(Prefs.BANKING_LOGIN, id, login);
        Prefs.set(Prefs.BANKING_BLZ, id, blz);
        Prefs.set(Prefs.BANKING_SERVER, id, bankingServer);
        Prefs.set(Prefs.NAME, id, name);
        Prefs.set(Prefs.SERVER, id, remotingHost);
        Prefs.set(Prefs.LABEL_PRINTER, id, labelPrinterUrl);
        Prefs.set(Prefs.RECHNUNG_XSLT, id, rechnungXslt);
        Prefs.set(Prefs.BESTELLUNG_XSLT, id, bestellungXslt);
        Prefs.set(Prefs.LAUFZETTEL_PRINTER, id, laufzettelPrinter);
        Prefs.set(Prefs.LOCAL_LABEL_PRINTER, localLabelPrinter);
        Prefs.set(Prefs.LOCAL_LAUFZETTEL_PRINTER, localLaufzettelPrinter);
    }

    public ShopConfiguration(final String id, final AuthtokenCache cache) {
        this(id);
        auth = cache.update(id);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAuth() {
        return auth;
    }

    public String getBankingServer() {
        return bankingServer;
    }

    public void setBankingServer(final String bankingServer) {
        this.bankingServer = bankingServer;
    }

    public String getBestellungXslt() {
        return bestellungXslt;
    }

    public void setBestellungXslt(final String bestellungXslt) {
        this.bestellungXslt = bestellungXslt;
    }

    public String getBlz() {
        return blz;
    }

    public void setBlz(final String blz) {
        this.blz = blz;
    }

    public String getId() {
        return id;
    }

    public String getKto() {
        return kto;
    }

    public void setKto(final String kto) {
        this.kto = kto;
    }

    public String getLabelPrinterUrl() {
        return labelPrinterUrl;
    }

    public void setLabelPrinterUrl(final String labelPrinterUrl) {
        this.labelPrinterUrl = labelPrinterUrl;
    }

    public String getLaufzettelPrinter() {
        return laufzettelPrinter;
    }

    public void setLaufzettelPrinter(final String laufzettelPrinter) {
        this.laufzettelPrinter = laufzettelPrinter;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(final String pin) {
        this.pin = pin;
    }

    public String getRechnungXslt() {
        return rechnungXslt;
    }

    public void setRechnungXslt(final String text) {
        this.rechnungXslt = text;
    }

    public String getRemotingHost() {
        return remotingHost;
    }

    public boolean isSafePin() {
        return safePin;
    }

    public void setSafePin(final boolean safePin) {
        this.safePin = safePin;
    }

// -------------------------- OTHER METHODS --------------------------

    public String getLocalPrinter(final PrinterTyp label) {
        switch (label) {
            case LABEL:
                return localLabelPrinter;
            case LAUFZETTEL:
                return localLaufzettelPrinter;
            default:
                throw new RuntimeException("unknown label type " + label);
        }
    }

    public void setRemoteHost(final String remoteHost) {
        this.remotingHost = remoteHost;
    }

    /*
public static ShopConfiguration loadLocalConfiguration(final ConfigurationDialog dialog) {
final ShopConfiguration configuration = new ShopConfiguration();
configuration.localLaufzettelPrinter = Prefs.getString(Prefs.LOCAL_LAUFZETTEL_PRINTER);
configuration.localLabelPrinter = Prefs.getString(Prefs.LOCAL_LABEL_PRINTER);
if (!dialog.isDataComplete(configuration)) {
if (!dialog.showDialog(configuration)) {
throw new RuntimeException("abbruch");
}
configuration.store();
}

return configuration;
}                         */

    public void store() {
        Prefs.set(Prefs.LOCAL_LABEL_PRINTER, localLabelPrinter);
        Prefs.set(Prefs.LOCAL_LAUFZETTEL_PRINTER, localLaufzettelPrinter);
    }
}