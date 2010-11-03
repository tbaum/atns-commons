package de.atns.printing.device;

import de.atns.printing.document.DocumentElement;

import java.io.IOException;

public abstract class AbstractDevice implements Device {
// ------------------------------ FIELDS ------------------------------

    protected int dpi;

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getDpi() {
        return this.dpi;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Device ---------------------

    public abstract void renderDocument(DocumentElement doc) throws IOException;
}
