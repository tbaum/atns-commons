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
import de.atns.printing.document.TextElement;

import java.io.UnsupportedEncodingException;

/**
 * @author Thomas Baum
 */
public class TextRenderer extends AbstractRenderer<TextElement> {
// --------------------------- CONSTRUCTORS ---------------------------

    public TextRenderer(final DocumentRenderer dr) {
        super(dr);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Renderer ---------------------

    public void render(final TextElement element) throws UnsupportedEncodingException {
        if (element.getText().length() > 3072)
            throw new IllegalArgumentException("Text too long, only 3072 characters supported");

        final int fontSize = Converter.convertMMToDots(element.getSize(), this.dr.getResolution());

        this.dr.getBuffer().append(getPositionString(element));
        this.dr.getBuffer().append("^A0,N,").append(fontSize); // Font Size
        this.dr.getBuffer().append("^FH");
        this.dr.getBuffer().append("^FD");
        appendEscaped(this.dr.getBuffer(), element.getText());
        this.dr.getBuffer().append("^FS\r\n");
    }
}
