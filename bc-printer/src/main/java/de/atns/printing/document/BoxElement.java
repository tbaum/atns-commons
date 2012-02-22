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
public class BoxElement extends AbstractElement {

    private final int size;

    private final double x2;
    private final double y2;

    public BoxElement(final double x1, final double y1, final double x2, final double y2, final int size) {
        super(x1, y1);
        this.x2 = x2 - x1;
        this.y2 = y2 - y1;
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    @Override public String toString() {
        return "BoxElement{size=" + size + ", x2=" + x2 + ", y2=" + y2 + ", " + super.toString() + "}";
    }
}
