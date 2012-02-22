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

/**
 * @author Steffen Schoenwiese
 */
public class TextElement extends AbstractElement {

    private double size;

    private String text;

    protected TextElement(final double x, final double y, final double size) {
        super(x, y);
        this.size = size;
    }

    public TextElement(final String text, final double x, final double y) {
        this(x, y, 1);
        this.text = text;
    }

    public TextElement(final String text, final double x, final double y, final double size) {
        this(x, y, size);
        this.text = text;
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(final double size) {
        this.size = size;
    }

    public String getText() {
        return this.text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override public String toString() {
        return "TextElement{text='" + text + "', " + super.toString() + "}";
    }
}
