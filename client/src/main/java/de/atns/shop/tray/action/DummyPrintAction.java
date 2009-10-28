package de.atns.shop.tray.action;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import de.atns.shop.tray.Action;
import de.atns.shop.tray.data.Data;
import de.atns.shop.tray.data.Printer;
import de.atns.shop.tray.gui.DebugLabelDialog;

@RequestScoped public class DummyPrintAction implements Action {
// ------------------------------ FIELDS ------------------------------

    private final Data data;
    private final Printer printerType;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public DummyPrintAction(final Data data,
                                    final Printer printerType) {
        this.data = data;
        this.printerType = printerType;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

    @Override public boolean localOnly() {
        return false;
    }

    @Override public void service() {
        new DebugLabelDialog(printerType.getPrinterType(), data.getData());
    }
}