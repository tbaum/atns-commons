package de.atns.printing.device;

import de.atns.printing.document.DocumentElement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageRendererDevice extends BufferedImageRenderDevice {

    private Image img;

    @Override public void renderDocument(final DocumentElement doc, final int quantity) throws IOException {
        // TODO Auto-generated method stub
    }

    public Image getImage() {
        return this.img;
    }

    @Override protected void processImage(final BufferedImage image) {
        this.img = image;
    }
}
