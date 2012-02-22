package de.atns.printing.environment;

import de.atns.printing.device.Device;

public abstract class AbstractPrinterImpl implements Printer {

    protected Device device;

    protected Material material;

    protected String name;

    protected PrinterState state = new PrinterState();

    @Override public Device getDevice() {
        return this.device;
    }

    @Override public void setDevice(final Device device) {
        this.device = device;
    }

    @Override public Material getMaterial() {
        return this.material;
    }

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
