/**
 *
 * Copyright (c) 2005 ATNS GmbH & Co.KG. All Rights Reserved.
 *
 * This file may not be reproduced in any form or by any means (graphic,
 * electronic, or mechanical) without written permission from ATNS. The
 * content of this file is subject to change without notice.
 *
 * ATNS does not assume liability for the use of this file or the results
 * obtained from using it.
 *
 **/

package de.atns.printing.renderer;

import de.atns.printing.document.DocumentElement;
import de.atns.printing.document.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <DOC>
 * @author Thomas Baum
 */
public abstract class AbstractRendererFactory<DOC extends DocumentRenderer> implements RendererFactory<DocumentRenderer> {

    protected Map<Class, Renderer> renderes = new HashMap<Class, Renderer>();

    private final DOC documentRenderer;

    protected AbstractRendererFactory(final DOC documentRenderer) {
        this.documentRenderer = documentRenderer;
        documentRenderer.setFactory(this);
        this.renderes.put(DocumentElement.class, documentRenderer);
    }

    @Override public DOC getDocumentRenderer() {
        return this.documentRenderer;
    }

    @Override public Renderer getRender(final Element element) {
        final Renderer r = this.renderes.get(element.getClass());
        if (r == null)
            throw new RuntimeException("missing Renderer for class " + element.getClass());
        return r;
    }

    @Override public void renderDocument(final DocumentElement document) throws IOException {
        getDocumentRenderer().render(document);
    }
}
