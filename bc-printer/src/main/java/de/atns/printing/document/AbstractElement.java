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
public abstract class AbstractElement implements Element {

    private Rotation rot;

    private double x;

    private double y;

    protected AbstractElement(final double x, final double y) {
        this(x, y, Rotation.NORMAL);
    }

    protected AbstractElement(final double x, final double y, final Rotation rot) {
        this.x = x;
        this.y = y;
        this.rot = rot;
    }

    public Rotation getRot() {
        return this.rot;
    }

    public void setRot(final Rotation rot) {
        this.rot = rot;
    }

    public double getX() {
        return this.x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    @Override public String toString() {
        return "rot=" + rot + ", x=" + x + ", y=" + y;
    }

    public enum Rotation {
        NORMAL, ROTATED, INVERTED, BOTTOMUP;
    }
}
