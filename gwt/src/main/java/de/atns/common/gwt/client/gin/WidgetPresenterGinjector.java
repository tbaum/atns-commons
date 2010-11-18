package de.atns.common.gwt.client.gin;

import com.google.gwt.inject.client.Ginjector;
import de.atns.common.gwt.client.WidgetPresenter;

/**
 * @author tbaum
 * @since 18.11.10
 */
public interface WidgetPresenterGinjector<T extends WidgetPresenter> extends Ginjector {
// -------------------------- OTHER METHODS --------------------------

    T presenter();

    SharedServicesAware sharedServicesAware();
}
