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
public class BarcodeElement extends AbstractElement {
// ------------------------------ FIELDS ------------------------------

    public static final double MODULO1 = 0.254 / 3;

    public static final double MODULO2 = 0.254 / 3 * 2;

    public static final double MODULO3 = 0.254;

    public static final double MODULO4 = 0.254 / 3 * 4;

    public static final double MODULO5 = 0.254 / 3 * 5;

    public static final double MODULO6 = 0.254 / 3 * 6;

    private String barcode;

    private Type type;

    private double height;

    private double modulo = MODULO3;

    private boolean interpretationLine = true;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * @param x
     * @param y
     * @param height
     * @param type
     */
    public BarcodeElement(final double x, final double y, final double height, final Type type) {
        super(x, y);
        this.type = type;
        this.height = height;
    }

    /**
     * @param x
     * @param y
     * @param height
     * @param type
     * @param msg
     */
    public BarcodeElement(final double x, final double y, final double height, final Type type, final String msg) {
        this(x, y, height, type);
        this.barcode = msg;
    }

    /**
     * @param x
     * @param y
     * @param height
     * @param type
     * @param modulo
     * @param msg
     */
    public BarcodeElement(final double x, final double y, final double height, final Type type, final double modulo, final String msg) {
        this(x, y, height, type);
        this.barcode = msg;
        this.modulo = modulo;
    }

    /**
     * @param x
     * @param y
     * @param height
     * @param type
     * @param modulo
     * @param interpretationLine
     * @param msg
     */
    public BarcodeElement(final double x, final double y, final double height, final Type type, final double modulo, final boolean interpretationLine, final String msg) {
        this(x, y, height, type);
        this.barcode = msg;
        this.modulo = modulo;
        this.interpretationLine = interpretationLine;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(final String barcode) {
        this.barcode = barcode;
    }

    public double getHeight() {
        return this.height;
    }

    public double getModulo() {
        return this.modulo;
    }

    public void setModulo(final double m) {
        this.modulo = m;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(final Type t) {
        this.type = t;
    }

    public boolean isInterpretationLine() {
        return this.interpretationLine;
    }

    public void setInterpretationLine(final boolean interpretationLine) {
        this.interpretationLine = interpretationLine;
    }

// -------------------------- ENUMERATIONS --------------------------

    public enum Type {
        CODE128, CODE39, EAN13, EAN128;
    }
}
