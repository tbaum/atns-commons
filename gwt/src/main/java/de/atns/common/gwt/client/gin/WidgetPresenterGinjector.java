package de.atns.common.gwt.client.gin;

import de.atns.common.gwt.client.WidgetPresenter;

/**
 * @author tbaum
 * @since 18.11.10
 */
public interface WidgetPresenterGinjector<T extends WidgetPresenter> extends SharedServicesGinjector {

    T presenter();
}
