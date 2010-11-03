package de.atns.printing.environment;

import de.atns.printing.device.ImageRendererDevice;

import java.awt.*;

public class ImagePrinter extends AbstractPrinterImpl {
// --------------------------- CONSTRUCTORS ---------------------------

    public ImagePrinter() {
        this.device = new ImageRendererDevice();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Printer ---------------------

    public boolean canPrint(@SuppressWarnings("unused") final
                            Label lf) {
        return true;
    }

    public void reset() {
        // ignored
    }

    public void safePrinterConfiguration() {
        // ignored
    }

    public boolean statusOk() {
        return true;
    }

    public boolean testMaterial(@SuppressWarnings("unused") final
                                Material mat) {
        // ignored
        return true;
    }

    public void updateState() {
        // ignored
    }

    public boolean waitForPrinter() {
        // ignored
        return false;
    }

// -------------------------- OTHER METHODS --------------------------

    public Image getImage() {
        return ((ImageRendererDevice) this.device).getImage();
    }
}
