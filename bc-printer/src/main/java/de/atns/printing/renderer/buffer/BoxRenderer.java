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
import de.atns.printing.document.BoxElement;
import de.atns.printing.renderer.Renderer;

import java.awt.*;

/**
 * @author Thomas Baum
 */
public class BoxRenderer implements Renderer<BoxElement> {
// ------------------------------ FIELDS ------------------------------

    private final DocumentRenderer dr;

// --------------------------- CONSTRUCTORS ---------------------------

    public BoxRenderer(final DocumentRenderer dr) {
        this.dr = dr;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Renderer ---------------------

    @Override public void render(final BoxElement element) {
        final Graphics graphics = this.dr.getGraphics();

        final int x = Converter.convertMMToDots(element.getX(), dr.getResolution());
        final int y = Converter.convertMMToDots(element.getY(), dr.getResolution());

        final int w = Converter.convertMMToDots(element.getX2(), dr.getResolution());
        final int h = Converter.convertMMToDots(element.getY2(), dr.getResolution());
        final int d = Converter.convertMMToDots(element.getSize(), dr.getResolution());

        graphics.drawLine(x, y, x + w, y + h);
    }
}
