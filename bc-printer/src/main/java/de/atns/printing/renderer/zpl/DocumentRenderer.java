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
import de.atns.printing.device.Device;
import de.atns.printing.document.DocumentElement;
import de.atns.printing.document.Element;
import de.atns.printing.document.Mode;
import de.atns.printing.renderer.AbstractDocumentRenderer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * @author Thomas Baum
 */
public class DocumentRenderer extends AbstractDocumentRenderer {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(DocumentRenderer.class);

    private StringBuffer buffer;

// --------------------------- CONSTRUCTORS ---------------------------

    public DocumentRenderer(final Device device) {
        super(device);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public StringBuffer getBuffer() {
        return this.buffer;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Renderer ---------------------

    @Override @SuppressWarnings("unchecked")
    public void render(final DocumentElement element) throws IOException {
        render(element, 1);
    }

// -------------------------- OTHER METHODS --------------------------

    @SuppressWarnings("unchecked")
    public void render(final DocumentElement element, final int quantity) throws IOException {
        this.buffer = new StringBuffer();
        this.buffer.append("^XA");
        this.buffer.append("^PQ").append(quantity);
        this.buffer.append("^LH0,0");
        this.buffer.append("^LL").append(Converter.convertMMToDots(element.getHeight(), getResolution())).append("\r\n");
        this.buffer.append("^PW").append(Converter.convertMMToDots(element.getWidth(), getResolution())).append("\r\n");

        this.buffer.append("^MNY"); // media tracking: non continuous
        this.buffer.append("^MMT");  // print mode: tear off
        this.buffer.append("^MFF,F");  // media feed: C: calibrate, Feed first web

        this.buffer.append("^MT").append(element.getMode().equals(Mode.TD) ? "D" : "T");
        this.buffer.append("^MD").append(element.getMode().equals(Mode.TD) ? "0" : "30");

        for (final Element e : element.getElements()) {
            this.factory.getRender(e).render(e);
        }
        this.buffer.append("^XZ\r\n");
        LOG.debug("Buffer: ");
        LOG.debug(this.buffer);
    }
}
