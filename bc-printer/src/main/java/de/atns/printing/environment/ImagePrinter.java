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

    @Override public boolean canPrint(@SuppressWarnings("unused") final
                            Label lf) {
        return true;
    }

    @Override public void reset() {
        // ignored
    }

    @Override public void safePrinterConfiguration() {
        // ignored
    }

    @Override public boolean statusOk() {
        return true;
    }

    @Override public boolean testMaterial(@SuppressWarnings("unused") final
                                Material mat) {
        // ignored
        return true;
    }

    @Override public void updateState() {
        // ignored
    }

    @Override public boolean waitForPrinter() {
        // ignored
        return false;
    }

// -------------------------- OTHER METHODS --------------------------

    public Image getImage() {
        return ((ImageRendererDevice) this.device).getImage();
    }
}
