package de.atns.printing.device;

import de.atns.printing.document.DocumentElement;
import de.atns.printing.renderer.zpl.RendererFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class ZPLPrinterDevice extends AbstractDevice {
// ------------------------------ FIELDS ------------------------------

    protected RendererFactory factory;

// --------------------------- CONSTRUCTORS ---------------------------

    protected ZPLPrinterDevice() {
        this.dpi = 300;
        this.factory = new RendererFactory(this);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Device ---------------------

    @Override
    public void renderDocument(final DocumentElement doc) throws IOException {
        this.factory.getDocumentRenderer().render(doc);
        final StringBuffer buffer = this.factory.getDocumentRenderer().getBuffer();
        processInstructions(buffer);
    }

    public void renderDocument(final DocumentElement doc, final int quantity) throws IOException {
        this.factory.getDocumentRenderer().render(doc, quantity);
        final StringBuffer buffer = this.factory.getDocumentRenderer().getBuffer();
        processInstructions(buffer);
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract void processInstructions(StringBuffer sb) throws UnsupportedEncodingException, IOException;
}
