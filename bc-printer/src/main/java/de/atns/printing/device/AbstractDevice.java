package de.atns.printing.device;

import de.atns.printing.document.DocumentElement;

import java.io.IOException;

public abstract class AbstractDevice implements Device {
// ------------------------------ FIELDS ------------------------------

    protected int dpi;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override public int getDpi() {
        return this.dpi;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Device ---------------------

    @Override public abstract void renderDocument(DocumentElement doc) throws IOException;
}
