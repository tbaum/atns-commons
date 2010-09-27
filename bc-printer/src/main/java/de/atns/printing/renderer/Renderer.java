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

import java.io.IOException;

import de.atns.printing.document.Element;

/**
 * Renders given elemenent (uses DocumentRenderer)
 * 
 * @author Thomas Baum
 * 
 * @param <E>
 */
public interface Renderer<E extends Element> {

    public void render(E element) throws IOException;

}
