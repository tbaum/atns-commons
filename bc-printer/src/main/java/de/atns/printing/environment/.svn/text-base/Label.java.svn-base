package de.atns.printing.environment;

import java.io.Serializable;

import de.atns.printing.document.DocumentElement;

public class Label implements Serializable {

    private static final long serialVersionUID = -328921583065260180L;

    protected DocumentElement document;

    protected Material material = Material.TD_21786;

    public Label() {
        // empty Constructor
    }
    
    public Label(final DocumentElement element) {
        this.document = element;
    }
    
    public Label(final DocumentElement element, final Material material) {
        this.document = element;
        this.material = material;
    }

    public DocumentElement getDocument() {
        return this.document;
    }

    public Material getMaterial() {
        return this.material;
    }

}
