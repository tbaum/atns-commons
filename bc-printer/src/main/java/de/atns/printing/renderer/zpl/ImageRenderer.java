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

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.atns.printing.document.ImageElement;

/**
 * 
 * @author Thomas Baum
 * 
 */
public class ImageRenderer extends AbstractRenderer<ImageElement>  {

    public ImageRenderer(final DocumentRenderer dr) {
        super(dr);
    }

    public void render(final ImageElement element) {
        final StringBuffer buffer = this.dr.getBuffer();
        ImageIcon ic = new ImageIcon(element.getImage());

        // rescale image with zoom
        ic = new ImageIcon(ic.getImage().getScaledInstance((int) (element.getScale() * ic.getIconWidth()),
                (int) (element.getScale() * ic.getIconHeight()), Image.SCALE_AREA_AVERAGING));

        // create pixel-buffer
        final BufferedImage bthumb = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_BYTE_GRAY);
        bthumb.getGraphics().drawImage(ic.getImage(), 0, 0, ic.getIconWidth(), ic.getIconHeight(), null);

        final int bytePerRow = (bthumb.getWidth() + 7) / 8;
        final int allBytes = bytePerRow * (bthumb.getHeight() - 1);

        buffer.append(getPositionString(element));
        buffer.append("^GFA,").append(allBytes).append(",").append(allBytes).append(",").append(bytePerRow).append(",");

        for (int y = 0; y < bthumb.getHeight(); y++) {
            int ret = 0;
            for (int x = 0; x < bthumb.getWidth(); x++) {
                final int gray = ((bthumb.getRGB(x, y) & 0xff) + ((bthumb.getRGB(x, y) >> 8) & 0xff) + ((bthumb.getRGB(x, y) >> 16) & 0xff)) / 3;
                ret |= (gray > 127 ? 0 : 0x80) >> (x & 0x07);
                if ((x & 0x07) == 0x07) {
                    buffer.append(toHex(ret));
                    ret = 0;
                }
            }
            if ((bthumb.getWidth()) % 8 != 0) {
                buffer.append(toHex(ret));
            }
            buffer.append("\r\n");
        }
        buffer.append("\r\n");

    }

    private String toHex(final int ret) {
        return (ret < 0x10 ? "0" : "") + Integer.toHexString(ret).toUpperCase();
    }

}
