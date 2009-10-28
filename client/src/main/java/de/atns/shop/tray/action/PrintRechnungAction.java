package de.atns.shop.tray.action;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import de.atns.shop.tray.Action;
import de.atns.shop.tray.RemoteShopService;
import de.atns.shop.tray.data.IdList;
import de.atns.shop.tray.data.Save;
import de.atns.shop.tray.data.ShopConfiguration;
import de.atns.shop.tray.printing.FopUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestScoped public class PrintRechnungAction implements Action {
// ------------------------------ FIELDS ------------------------------

    private final IdList idList;
    private final RemoteShopService remote;
    private final Save saveFile;
    private final String xsltSource;
    private final String shopId;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public PrintRechnungAction(final ShopConfiguration configuration,
                                       final RemoteShopService remote,
                                       final IdList idList,
                                       final Save save) {
        this.shopId = configuration.getId();
        this.xsltSource = configuration.getRechnungXslt();
        this.remote = remote;
        this.idList = idList;
        this.saveFile = save;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

    @Override public boolean localOnly() {
        return true;
    }

    @Override public void service() {
        printDocuments(retrieveDocuments());
    }

// -------------------------- OTHER METHODS --------------------------

    private void printDocuments(final Map<String, String> documents) {
        for (final String vorgangId1 : documents.keySet()) {
            if (saveFile.isTrue()) {
                FopUtil.save(documents.get(vorgangId1), xsltSource, createFileName(vorgangId1));
            } else {
                FopUtil.print(documents.get(vorgangId1), xsltSource);
            }
        }
    }

    private String createFileName(final String vorgangId1) {
        final Date datum = remote.getRechnungDruckdatum(vorgangId1);
        final long nr = remote.getRechnungNr(vorgangId1);
        final String datumStr = new SimpleDateFormat("yyMMddHHmm").format(datum);
        return "R" + shopId + "_" + nr + "_" + datumStr + ".pdf";
    }

    private Map<String, String> retrieveDocuments() {
        final Map<String, String> documents = new HashMap<String, String>();
        for (final String id : idList) {
            documents.put(id, remote.getRechnungAndLieferschein(id));
        }
        return documents;
    }
}
