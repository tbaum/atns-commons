package de.atns.printing.device;

import de.atns.printing.document.DocumentElement;
import de.atns.printing.renderer.zpl.RendererFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class ZPLPrinterDevice extends AbstractDevice {
// ------------------------------ FIELDS ------------------------------

    protected RendererFactory factory;

// --------------------------- CONSTRUCTORS ---------------------------

    protected ZPLPrinterDevice(int dpi) {
        this.dpi = dpi;
        this.factory = new RendererFactory(this);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Device ---------------------

    @Override
    public void renderDocument(final DocumentElement doc) throws IOException {
        renderDocument(doc, 1);
    }

    @Override public void renderDocument(final DocumentElement doc, final int quantity) throws IOException {
        this.factory.getDocumentRenderer().render(doc, quantity);
        final StringBuffer buffer = this.factory.getDocumentRenderer().getBuffer();
        processInstructions(buffer);
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract void processInstructions(StringBuffer sb) throws UnsupportedEncodingException, IOException;
}
