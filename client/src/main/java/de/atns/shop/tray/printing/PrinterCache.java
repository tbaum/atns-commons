package de.atns.shop.tray.printing;

import java.util.HashMap;
import java.util.Map;

public class PrinterCache {
// ------------------------------ FIELDS ------------------------------

    private static final Map<String, Printer> printers = new HashMap<String, Printer>();

// -------------------------- STATIC METHODS --------------------------

    public static Printer getPrinter(final String portName) {
        Printer result = printers.get(portName);
        if (result == null) {
            result = new SerialPortPrinterImpl(portName);
            printers.put(portName, result);
        }
        return result;
    }
}
