package de.atns.printing.device;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import de.atns.printing.document.DocumentElement;
import de.atns.printing.renderer.zpl.RendererFactory;

public abstract class ZPLPrinterDevice extends AbstractDevice {

    protected RendererFactory factory;

    protected ZPLPrinterDevice() {
        this.dpi = 300;
        this.factory = new RendererFactory(this);
    }

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
    protected abstract void processInstructions(StringBuffer sb) throws UnsupportedEncodingException, IOException;

}
