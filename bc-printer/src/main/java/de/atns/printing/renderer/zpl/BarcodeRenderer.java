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

package de.atns.printing.renderer.zpl;

import de.atns.printing.Converter;
import de.atns.printing.document.BarcodeElement;

import java.io.UnsupportedEncodingException;

/**
 * @author Thomas Baum
 */
public class BarcodeRenderer extends AbstractRenderer<BarcodeElement> {

    public BarcodeRenderer(final DocumentRenderer dr) {
        super(dr);
    }

    public void render(final BarcodeElement element) throws UnsupportedEncodingException {
        final StringBuffer buffer = new StringBuffer();
        String dataPrefix = "";

        buffer.append(getPositionString(element));
        buffer.append(getRotation(element.getRot()));
        buffer.append("^BY").append(Converter.convertMMToDots(element.getModulo(), this.dr.getResolution())).append(",,").append(
                Converter.convertMMToDots(element.getHeight(), this.dr.getResolution()));

        switch (element.getType()) {
            case CODE39:
                buffer.append("^B3");
                break;
            case CODE128:
                if (element.isInterpretationLine())
                    buffer.append("^BC,,,,,N");
                else
                    buffer.append("^BC,,N,,,N");

                break;
            case EAN13:
                buffer.append("^BE");
                break;
            case EAN128:
                if (element.isInterpretationLine())
                    buffer.append("^BC,,,,,N");
                else
                    buffer.append("^BC,,N,,,N");
                if (element.getBarcode().matches("(\\d\\d)+")) {
//            if (element.getBarcode().matches("\\d*")) {
                    dataPrefix = ">;>8";
                } else {
                    dataPrefix = ">:>8";
                }
                break;
            default:
                throw new IllegalArgumentException("unable to render Barcode-Type " + element.getType());
        }
        buffer.append("^FH^FD").append(dataPrefix);
        appendEscaped(buffer, element.getBarcode());
        buffer.append("^FS");

        this.dr.getBuffer().append(buffer);
        this.dr.getBuffer().append("^FWN\n");
    }
}
