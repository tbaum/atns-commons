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
import de.atns.printing.document.BoxElement;

import java.io.UnsupportedEncodingException;

/**
 * @author Thomas Baum
 */
public class BoxRenderer extends AbstractRenderer<BoxElement> {

    public BoxRenderer(final DocumentRenderer dr) {
        super(dr);
    }

    public void render(final BoxElement element) throws UnsupportedEncodingException {

        final int w = Converter.convertMMToDots(element.getX2(), this.dr.getResolution());
        final int h = Converter.convertMMToDots(element.getY2(), this.dr.getResolution());

        this.dr.getBuffer().append(getPositionString(element));
        this.dr.getBuffer().append("^GB").append(w).append(",").append(h).append(",").append(element.getSize());
        this.dr.getBuffer().append("^FS");
        this.dr.getBuffer().append("\n");
    }
}
