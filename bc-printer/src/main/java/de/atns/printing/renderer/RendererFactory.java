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

/**
 * Registry for all renderes of <DOC>
 *
 * @param <DOC>
 * @author Thomas Baum
 */
public interface RendererFactory<DOC extends DocumentRenderer> {
// -------------------------- OTHER METHODS --------------------------

    /**
     * returns the document-renderer
     *
     * @param element
     * @return
     */
    public DOC getDocumentRenderer();

    /**
     * returns the renderer for this type of element
     *
     * @param element
     * @return
     */
    public Renderer getRender(Element element);

    /**
     * call: getDocumentRenderer().render(document)
     *
     * @param document
     * @throws IOException
     */
    public void renderDocument(DocumentElement document) throws IOException;
}
