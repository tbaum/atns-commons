package de.atns.common.crud.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import de.atns.common.gwt.client.ErrorWidgetDisplay;

/**
 * @author tbaum
 * @since 05.10.2010
 */
public interface ListWidgetDisplay<T> extends ErrorWidgetDisplay {
// -------------------------- OTHER METHODS --------------------------

    HandlerRegistration forPressEnter(KeyPressHandler keyPressHandler);

    HandlerRegistration forSuche(ClickHandler clickHandler);

    void setPagePresenter(PagePresenter.Display display);
}
