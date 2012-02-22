package de.atns.printing.device;

import de.atns.printing.document.DocumentElement;

import java.io.IOException;

public abstract class AbstractDevice implements Device {

    protected int dpi;

    @Override public int getDpi() {
        return this.dpi;
    }

    @Override public abstract void renderDocument(DocumentElement doc) throws IOException;
}
