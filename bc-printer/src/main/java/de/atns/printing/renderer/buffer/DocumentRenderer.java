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

import de.atns.printing.device.Device;
import de.atns.printing.document.DocumentElement;
import de.atns.printing.document.Element;
import de.atns.printing.renderer.AbstractDocumentRenderer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static de.atns.printing.Converter.convertMMToDots;

/**
 * @author Thomas Baum
 */
public class DocumentRenderer extends AbstractDocumentRenderer {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(DocumentRenderer.class);

    private Graphics graphics;

    private BufferedImage image;

// --------------------------- CONSTRUCTORS ---------------------------

    protected DocumentRenderer(final Device device) {
        super(device);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Graphics getGraphics() {
        return this.graphics;
    }

    public BufferedImage getImage() {
        return this.image;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Renderer ---------------------

    @SuppressWarnings("unchecked")
    public void render(final DocumentElement element) throws IOException {
        final int width = convertMMToDots(element.getWidth(), getResolution());
        final int height = convertMMToDots(element.getHeight(), getResolution());

        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        LOG.debug(convertMMToDots(element.getWidth(), getResolution()) + " " +
                convertMMToDots(element.getHeight(), getResolution()));
        this.graphics = this.image.getGraphics();
        this.graphics.setColor(new Color(250, 250, 250));
        this.graphics.fillRect(0, 0, convertMMToDots(element.getWidth(), getResolution()),
                convertMMToDots(
                        element.getHeight(), getResolution()));
        this.graphics.setColor(Color.BLACK);
        for (final Element e : element.getElements()) {
            this.factory.getRender(e).render(e);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public void reset() {
        this.image = null;
        this.graphics = null;
    }
}