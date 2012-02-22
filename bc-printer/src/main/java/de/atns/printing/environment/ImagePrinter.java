package de.atns.printing.environment;

import de.atns.printing.device.ImageRendererDevice;

import java.awt.*;

public class ImagePrinter extends AbstractPrinterImpl {

    public ImagePrinter() {
        this.device = new ImageRendererDevice();
    }

    @Override public boolean canPrint(@SuppressWarnings("unused") final Label lf) {
        return true;
    }

    @Override public void reset() {
    }

    @Override public void safePrinterConfiguration() {
    }

    @Override public boolean statusOk() {
        return true;
    }

    @Override public boolean testMaterial(@SuppressWarnings("unused") final Material mat) {
        return true;
    }

    @Override public void updateState() {
    }

    @Override public boolean waitForPrinter() {
        return false;
    }

    public Image getImage() {
        return ((ImageRendererDevice) this.device).getImage();
    }
}
