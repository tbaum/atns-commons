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

package de.atns.printing.renderer.buffer;

import de.atns.printing.Converter;
import de.atns.printing.document.TextElement;
import de.atns.printing.renderer.Renderer;

import java.awt.*;

/**
 * @author Thomas Baum
 */
public class TextRenderer implements Renderer<TextElement> {
// ------------------------------ FIELDS ------------------------------

    private DocumentRenderer dr;

// --------------------------- CONSTRUCTORS ---------------------------

    public TextRenderer(final DocumentRenderer dr) {
        this.dr = dr;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Renderer ---------------------

    public void render(final TextElement element) {
        final Font font = new Font("sans", Font.PLAIN, Converter.convertMMToDots(element.getSize() * 0.9, this.dr.getResolution()));
        final Graphics graphics = this.dr.getGraphics();

        graphics.setFont(font);
        final int x = Converter.convertMMToDots(element.getX(), dr.getResolution());
        final int y = (int) (Converter.convertMMToDots(element.getY(), dr.getResolution()) + graphics.getFontMetrics().getMaxAscent() * 0.8);
        graphics.drawString((element).getText(), x, y);
    }
}
