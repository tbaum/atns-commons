package de.atns.printing.environment;

import de.atns.printing.device.Device;

public abstract class AbstractPrinterImpl implements Printer {
// ------------------------------ FIELDS ------------------------------

    protected Device device;

    protected Material material;

    protected String name;

    protected PrinterState state = new PrinterState();

// --------------------- GETTER / SETTER METHODS ---------------------

    /*
     * (non-Javadoc)
     * 
     * @see de.atns.printing.environment.Printer#getDevice()
     */
    @Override public Device getDevice() {
        return this.device;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.atns.printing.environment.Printer#setDevice(de.atns.printing.device.Device)
     */
    @Override public void setDevice(final Device device) {
        this.device = device;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.atns.printing.environment.Printer#getMaterial()
     */
    @Override public Material getMaterial() {
        return this.material;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.atns.printing.environment.Printer#setMaterial(de.atns.printing.environment.Material)
     */
    @Override public void setMaterial(final Material material) {
        this.material = material;
    }

    @Override public String getName() {
        return this.name;
    }

    @Override public void setName(final String name) {
        this.name = name;
    }

    @Override public PrinterState getState() {
        return this.state;
    }

    @Override public void setState(final PrinterState state) {
        this.state = state;
    }
}
