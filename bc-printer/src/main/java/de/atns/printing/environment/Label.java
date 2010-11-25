package de.atns.printing.environment;

import de.atns.printing.document.DocumentElement;

import java.io.Serializable;

public class Label implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -328921583065260180L;

    protected DocumentElement document;

    protected Material material = Material.TD_21786;
    private final int anzahl;

// --------------------------- CONSTRUCTORS ---------------------------

    public Label(final DocumentElement element) {
        this.document = element;
        anzahl = 1;
    }

    public Label(final DocumentElement element, final Material material, final int anzahl) {
        this.document = element;
        this.material = material;
        this.anzahl = anzahl;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getAnzahl() {
        return anzahl;
    }

    public DocumentElement getDocument() {
        return this.document;
    }

    public Material getMaterial() {
        return this.material;
    }
}
