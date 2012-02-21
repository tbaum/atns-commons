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

package de.atns.printing.renderer;

import de.atns.printing.device.Device;

/**
 * @author Thomas Baum
 */
public abstract class AbstractDocumentRenderer implements DocumentRenderer {
// ------------------------------ FIELDS ------------------------------

    protected Device device;

    protected RendererFactory factory;

// --------------------------- CONSTRUCTORS ---------------------------

    protected AbstractDocumentRenderer(final Device device) {
        this.device = device;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override public void setFactory(final RendererFactory factory) {
        this.factory = factory;
    }

// -------------------------- OTHER METHODS --------------------------

    public int getResolution() {
        return this.device.getDpi();
    }
}
