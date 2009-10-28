package de.atns.shop.tray.action;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import de.atns.shop.tray.Action;
import de.atns.shop.tray.PrinterTyp;
import de.atns.shop.tray.RemoteShopService;
import de.atns.shop.tray.data.Id;
import de.atns.shop.tray.data.ShopConfiguration;

@RequestScoped public class PrintLabelAction implements Action {
// ------------------------------ FIELDS ------------------------------

    private final Id id;
    private final RemoteShopService remote;
    private final String labelPrinterUrl;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public PrintLabelAction(final ShopConfiguration configuration,
                                    final RemoteShopService remote,
                                    final Id id) {
        this.labelPrinterUrl = configuration.getLabelPrinterUrl();
        this.remote = remote;
        this.id = id;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

    @Override public boolean localOnly() {
        return true;
    }

    @Override public void service() {
        final String data = remote.getLabelForId(id);
        remote.sendTo(labelPrinterUrl, data, PrinterTyp.LABEL);
    }
}