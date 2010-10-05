package de.atns.common.crud.client;

import de.atns.common.gwt.client.ErrorWidgetDisplay;

/**
 * Created by IntelliJ IDEA.
 * User: tbaum
 * Date: 05.10.2010
 * Time: 01:03:48
 * To change this template use File | Settings | File Templates.
 */
public interface ListWidgetDisplay extends ErrorWidgetDisplay {
// -------------------------- OTHER METHODS --------------------------

    void setPagePresenter(PagePresenter.Display display);
}
