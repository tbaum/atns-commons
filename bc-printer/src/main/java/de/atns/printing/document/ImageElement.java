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

package de.atns.printing.document;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Steffen Schoenwiese
 */
public class ImageElement extends AbstractElement {
// ------------------------------ FIELDS ------------------------------

    private Image image;

    private double scale;

// --------------------------- CONSTRUCTORS ---------------------------

    protected ImageElement(final double x, final double y, final double scale) {
        super(x, y);
        this.scale = scale;
    }

    public ImageElement(final Image img, final double x, final double y, final double zoom) {
        this(x, y, zoom);
        this.image = img;
    }

    public ImageElement(final String fileName, final double x, final double y, final double zoom) throws IOException {
        this(ImageIO.read(new File(fileName)), x, y, zoom);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Image getImage() {
        return this.image;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public double getScale() {
        return this.scale;
    }

    public void setScale(final double scale) {
        this.scale = scale;
    }
}
