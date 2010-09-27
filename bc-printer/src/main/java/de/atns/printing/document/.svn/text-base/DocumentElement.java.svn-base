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

import de.atns.printing.environment.Material;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Steffen Schoenwiese
 */
public class DocumentElement implements Element {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -4996631267175741673L;

    protected List<Element> elements = new LinkedList<Element>();

    private double height;

    private double width;

    private Mode mode = Mode.TD;

// --------------------------- CONSTRUCTORS ---------------------------

    public DocumentElement(final Material m) {
        this(m.getWidth(), m.getHeight(), m.getMode());
    }

    public DocumentElement(final double width, final double height, final Mode mode) {
        this.width = width;
        this.height = height;
        this.mode = mode;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public double getHeight() {
        return this.height;
    }

    public void setHeight(final double height) {
        this.height = height;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(final Mode mode) {
        this.mode = mode;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(final double width) {
        this.width = width;
    }

// -------------------------- OTHER METHODS --------------------------

    public void addElement(final Element elem) {
        if (!this.elements.contains(elem))
            this.elements.add(elem);
    }

    public Element[] getElements() {
        return this.elements.toArray(new Element[this.elements.size()]);
    }
}
