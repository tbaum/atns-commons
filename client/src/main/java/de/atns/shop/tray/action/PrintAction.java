package de.atns.shop.tray.action;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import de.atns.shop.tray.Action;
import de.atns.shop.tray.data.Data;
import de.atns.shop.tray.data.Printer;
import de.atns.shop.tray.data.ShopConfiguration;
import de.atns.shop.tray.printing.PrinterCache;

@RequestScoped public class PrintAction implements Action {
// ------------------------------ FIELDS ------------------------------

    private final Data data;
    private final Printer printerType;
    private final ShopConfiguration configuration;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public PrintAction(final ShopConfiguration configuration,
                               final Data data,
                               final Printer printerType) {
        this.configuration = configuration;
        this.data = data;
        this.printerType = printerType;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

    @Override public boolean localOnly() {
        return false;
    }

    @Override public void service() {
        final de.atns.shop.tray.printing.Printer printer = PrinterCache.getPrinter(configuration.getLocalPrinter(printerType.getPrinterType()));
        printer.printData(data.getData());
    }
}
