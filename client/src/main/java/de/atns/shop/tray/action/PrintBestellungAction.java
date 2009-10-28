package de.atns.shop.tray.action;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import de.atns.shop.tray.Action;
import de.atns.shop.tray.RemoteShopService;
import de.atns.shop.tray.data.Id;
import de.atns.shop.tray.data.ShopConfiguration;
import de.atns.shop.tray.printing.FopUtil;

@RequestScoped public class PrintBestellungAction implements Action {
// ------------------------------ FIELDS ------------------------------

    private final Id id;
    private final RemoteShopService remote;
    private final String xsltSource;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public PrintBestellungAction(final ShopConfiguration configuration,
                                         final RemoteShopService remote,
                                         final Id id) {
        this.xsltSource = configuration.getBestellungXslt();
        this.remote = remote;
        this.id = id;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

    @Override public boolean localOnly() {
        return true;
    }

    @Override public void service() {
        final String data = remote.getBestellung(id);
        FopUtil.convertFo2Awt(data, xsltSource);
    }
}