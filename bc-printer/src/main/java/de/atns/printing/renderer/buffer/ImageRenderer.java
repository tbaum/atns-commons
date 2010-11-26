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
import de.atns.printing.document.ImageElement;
import de.atns.printing.renderer.Renderer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Thomas Baum
 */
public class ImageRenderer implements Renderer<ImageElement> {
// ------------------------------ FIELDS ------------------------------

    private final DocumentRenderer dr;

// --------------------------- CONSTRUCTORS ---------------------------

    public ImageRenderer(final DocumentRenderer dr) {
        this.dr = dr;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Renderer ---------------------

    public void render(final ImageElement element) {
        // Image i = element.getImage();
        ImageIcon ic = new ImageIcon(element.getImage());

        // scale image
        ic = new ImageIcon(ic.getImage().getScaledInstance(//
                (int) (0.24 * element.getScale() * ic.getIconWidth()), //
                (int) (0.24 * element.getScale() * ic.getIconHeight()), //
                Image.SCALE_AREA_AVERAGING));

        this.dr.getGraphics().drawImage(ic.getImage(), //
                Converter.convertMMToDots(element.getX(), this.dr.getResolution()),//
                Converter.convertMMToDots(element.getY(), this.dr.getResolution()), null);
    }
}
