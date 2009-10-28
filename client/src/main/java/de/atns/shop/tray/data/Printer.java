package de.atns.shop.tray.data;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.RequestScoped;
import de.atns.shop.tray.PrinterTyp;
import static de.atns.shop.tray.Util.extractParameter;

import java.util.Map;

@RequestScoped public class Printer {
// ------------------------------ FIELDS ------------------------------

    private final PrinterTyp printer;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public Printer(@RequestParameters final Map<String, String[]> params) {
        this.printer = PrinterTyp.valueOf(extractParameter(params, "printer"));
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public PrinterTyp getPrinterType() {
        return printer;
    }
}