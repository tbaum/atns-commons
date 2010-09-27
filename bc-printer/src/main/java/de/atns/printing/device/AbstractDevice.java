package de.atns.printing.device;

import java.io.IOException;

import de.atns.printing.document.DocumentElement;

public abstract class AbstractDevice implements Device {

    protected int dpi;

    public abstract void renderDocument(DocumentElement doc) throws IOException;

    public int getDpi() {
        return this.dpi;
    }
}
