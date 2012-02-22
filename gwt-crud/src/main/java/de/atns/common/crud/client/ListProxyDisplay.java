package de.atns.common.crud.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.web.bindery.event.shared.HandlerRegistration;
import de.atns.common.gwt.client.WidgetDisplay;

/**
 * @author tbaum
 * @since 05.10.2010
 */
public interface ListProxyDisplay<T> extends WidgetDisplay {

    HandlerRegistration forPressEnter(KeyPressHandler keyPressHandler);

    HandlerRegistration forSuche(ClickHandler clickHandler);

    void setPageProxyPresenter(PageProxyPresenter.Display display);
}
