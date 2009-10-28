package de.atns.shop.tray.action;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import de.atns.shop.tray.Action;
import de.atns.shop.tray.PrinterTyp;
import de.atns.shop.tray.RemoteShopService;
import de.atns.shop.tray.data.Id;
import de.atns.shop.tray.data.ShopConfiguration;

@RequestScoped public class PrintLaufzettelAction implements Action {
// ------------------------------ FIELDS ------------------------------

    private final Id id;
    private final RemoteShopService remote;
    private final String laufzettelPrinter;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public PrintLaufzettelAction(final ShopConfiguration configuration,
                                         final RemoteShopService remote,
                                         final Id id) {
        this.laufzettelPrinter = configuration.getLaufzettelPrinter();
        this.remote = remote;
        this.id = id;
    }// ------------------------ INTERFACE METHODS ------------------------

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

    @Override public boolean localOnly() {
        return true;
    }

    @Override public void service() {
        final String laufzettel = remote.getLaufzettel(id);
        remote.sendTo(laufzettelPrinter, laufzettel, PrinterTyp.LAUFZETTEL);
    }
}