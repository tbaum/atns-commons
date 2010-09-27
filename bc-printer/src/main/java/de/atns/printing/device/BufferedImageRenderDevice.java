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

package de.atns.printing.device;

import java.awt.image.BufferedImage;
import java.io.IOException;

import de.atns.printing.document.DocumentElement;
import de.atns.printing.renderer.buffer.RendererFactory;

/**
 * 
 * @author Thomas Baum
 * 
 */
public abstract class BufferedImageRenderDevice extends AbstractDevice {

    private RendererFactory factory;

    public BufferedImageRenderDevice() {
        this.factory = new RendererFactory(this);
        this.dpi = 300;
    }

    @Override
    public void renderDocument(final DocumentElement doc) throws IOException {
        this.factory.getDocumentRenderer().render(doc);
        processImage(this.factory.getDocumentRenderer().getImage());
    }

    protected abstract void processImage(BufferedImage image);

}
