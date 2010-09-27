package de.atns.printing.device;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import de.atns.printing.document.DocumentElement;

public class ImageRendererDevice extends BufferedImageRenderDevice {

    private Image img;
    
    @Override
    protected void processImage(final BufferedImage image) {
        this.img = image;
    }
    
    public Image getImage() {
        return this.img;
    }

    public void renderDocument(final DocumentElement doc, final int quantity) throws IOException {
        // TODO Auto-generated method stub
        
    }

}
