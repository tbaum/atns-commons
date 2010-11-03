package de.atns.printing.environment;

import de.atns.printing.device.Device;

import java.io.IOException;

public interface Printer {
// -------------------------- OTHER METHODS --------------------------

    public abstract boolean canPrint(Label lf);

    public abstract Device getDevice();

    public abstract Material getMaterial();

    public abstract String getName();

    public abstract PrinterState getState();

    public void reset();

    public void safePrinterConfiguration();

    public abstract void setDevice(Device device);

    public abstract void setMaterial(Material material);

    public abstract void setName(String name);

    public abstract void setState(PrinterState state);

    public boolean statusOk();

    public boolean testMaterial(Material material) throws IOException;

    public void updateState() throws IOException;

    public boolean waitForPrinter();
}
