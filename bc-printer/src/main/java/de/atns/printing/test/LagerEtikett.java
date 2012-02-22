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
public class LagerEtikett {

    private final String address = "10.1.1.19";

    private final int port = 9100;

    public LagerEtikett() throws Exception {
        final String[] pr = {
                "A.01", "A.01", "A.01", "A.01", "A.02", "A.02", "A.02", "A.02", "A.03", "A.03", "A.03", "A.03", "A.04",
                "A.04", "A.04", "A.04", "A.05", "A.05", "A.05", "A.05", "A.06", "A.06", "A.06", "A.06", "A.07", "A.07",
                "A.07", "A.07", "A.08", "A.08", "A.08", "A.08", "A.09", "A.09", "A.09", "A.09", "A.10", "A.10", "A.10",
                "A.10", "A.10", "A.10", "A.10", "A.10", "A.11", "A.11", "A.11", "A.11", "A.12", "A.12", "A.12", "A.12",
                "A.13", "A.13", "A.13", "A.13", "A.14", "A.14", "A.14", "A.14", "A.15", "A.15", "A.15", "A.15", "B.01",
                "B.01", "B.01", "B.01", "B.02", "B.02", "B.02", "B.02", "B.03", "B.03", "B.03", "B.03", "B.04", "B.04",
                "B.04", "B.04", "B.05", "B.05", "B.05", "B.05", "C.01", "C.01", "C.01", "C.01", "C.02", "C.02", "C.02",
                "C.02", "C.03", "C.03", "C.03", "C.03"
        };
        final Device f = new ZPLNetworkPrinterDevice(address, port, 300);

        for (final String i : pr) {
            f.renderDocument(createLabel(i));
        }
//        testZLP(createLabel("B.02-D.04"));
    }

    private DocumentElement createLabel(final String s) {
        final DocumentElement label = new DocumentElement(51, 25, Mode.TT);
        label.addElement(new TextElement(s, 10, 8, 18));
        return label;
    }

    private DocumentElement createLabel1(final String s) {
        final DocumentElement label = new DocumentElement(51, 25, Mode.TT);
        label.addElement(new BarcodeElement(7, 6, 12, BarcodeElement.Type.EAN128, BarcodeElement.MODULO3, false,
                "99" + s.replaceAll(".", "")));
        label.addElement(new TextElement(s, 10, 20, 7));
        return label;
    }

    public void testSwing(final DocumentElement label) throws Exception {
        final Device device = new SwingGUIRenderDevice();
        device.renderDocument(label);
    }

    public void testZLP(@SuppressWarnings("unused") final DocumentElement label) throws Exception {
        final Device f = new ZPLNetworkPrinterDevice(address, port, 300);
        f.renderDocument(label);
    }

    public static void main(final String[] args) throws Exception {
        new LagerEtikett();
    }
}
