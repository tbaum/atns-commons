package de.atns.printing.environment;

import java.awt.Image;

import de.atns.printing.device.ImageRendererDevice;

public class ImagePrinter extends AbstractPrinterImpl {

    public ImagePrinter() {
        this.device = new ImageRendererDevice();
    }

    public boolean canPrint(@SuppressWarnings("unused") final
    Label lf) {
        return true;
    }

    public void updateState() {
        // ignored
    }

    public boolean statusOk() {
        return true;
    }

    public Image getImage() {
        return ((ImageRendererDevice) this.device).getImage();
    }

    public boolean testMaterial(@SuppressWarnings("unused") final
    Material mat) {
        // ignored
        return true;
    }

    public void reset() {
        // ignored
    }

    public boolean waitForPrinter() {
        // ignored
        return false;
    }

    public void safePrinterConfiguration() {
        // ignored
    }

}
