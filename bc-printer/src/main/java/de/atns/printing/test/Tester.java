/**
 *
 * Copyright (c) 2005 ATNS GmbH & Co.KG. All Rights Reserved.
 *
 * This file may not be reproduced in any form or by any means (graphic,
 * electronic, or mechanical) without written permission from ATNS. The
 * content of this file is subject to change without notice.
 *
 * ATNS does not assume liability for the use of this file or the results
 * obtained from using it.
 *
 **/

package de.atns.printing.test;

import de.atns.printing.device.Device;
import de.atns.printing.device.SwingGUIRenderDevice;
import de.atns.printing.device.ZPLNetworkPrinterDevice;
import de.atns.printing.document.BarcodeElement;
import de.atns.printing.document.DocumentElement;
import de.atns.printing.document.Mode;
import de.atns.printing.document.TextElement;

/**
 * @author Steffen Schoenwiese
 */
public class Tester {
// ------------------------------ FIELDS ------------------------------

    private final String address = "10.1.1.19";

    private final int port = 9100;

// --------------------------- CONSTRUCTORS ---------------------------

    public Tester() throws Exception {
        final DocumentElement label = createLabel();

//        PrinterEnvironment pe = new PrinterEnvironment();
//        Printer p = new PrinterImpl();
//        p.setName("PRN1");
//        p.setDevice(new ZPLNetworkPrinterDevice(this.address, this.port));
//        p.setMaterial(Material.TD_21786);
//        pe.addPrinter(p);
//
//        Label l = new Label(label, Material.TD_21786);
//        Printer p1 = pe.getPrinterForLabel(l);
//        if (p1 == null) {
//            System.err.println("No printer found");
//        } else {
//            if (pe.print(l, p1))
//                System.err.println("Drucken erfolgreich!");
//            else
//                System.err.println("Drucken NICHT erfolgreich!");
//        }
//
//        Thread.sleep(10000);
        testZLP(label);
        //testSwing(label);
    }

    private DocumentElement createLabel() {
        final DocumentElement label = new DocumentElement(51, 25, Mode.TT);
//        label.addElement(new TextElement("EAN-Code", 6, 32, 6));
        label.addElement(
                new BarcodeElement(7, 6, 12, BarcodeElement.Type.EAN128, BarcodeElement.MODULO3, false, "99B02-D04"));
        label.addElement(new TextElement("(99)B02-D04", 5, 20, 7));
//        label.addElement(new BarcodeElement(6, 82, 20, BarcodeElement.Type.EAN128, BarcodeElement.MODULO4,
//                "01012345678901283105123560"));
//        label.addElement(new BarcodeElement(6, 118, 20, BarcodeElement.Type.EAN128, BarcodeElement.MODULO4, "123456789098"));
//        label.addElement(new TextElement("MaterialImpl-/Mengencode", 6, 74, 6));
//        label.addElement(new TextElement("Haltbarkeits-/Batchcode", 6, 110, 6));
//        label.addElement(new TextElement("Gedruckt: " + new Date(), 6, 144, 2));
        return label;
    }

    public void testZLP(@SuppressWarnings("unused") final DocumentElement label) throws Exception {
        final Device f = new ZPLNetworkPrinterDevice(address, port, 300);
        f.renderDocument(label);
    }

// -------------------------- OTHER METHODS --------------------------

    public void testSwing(final DocumentElement label) throws Exception {
        final Device device = new SwingGUIRenderDevice();
        device.renderDocument(label);
    }

// --------------------------- main() method ---------------------------

    public static void main(final String[] args) throws Exception {
        new Tester();
    }
}
