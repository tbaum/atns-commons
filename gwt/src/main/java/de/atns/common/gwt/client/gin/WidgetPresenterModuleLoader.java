package de.atns.common.gwt.client.gin;

import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.Place;
import de.atns.common.gwt.client.WidgetPresenter;

/**
 * @author tbaum
 * @since 18.11.10
 */
public abstract class WidgetPresenterModuleLoader<T extends WidgetPresenter & PlacePresenter>
        extends SharedServicesModuleLoader<WidgetPresenterGinjector<T>>
        implements RunAsyncCallback {
// --------------------------- CONSTRUCTORS ---------------------------

    public WidgetPresenterModuleLoader(final SharedServices sharedServices) {
        super(sharedServices);
    }

// -------------------------- OTHER METHODS --------------------------

    public abstract boolean canHandlePlace(Place place);

    public T presenter() {
        return injector().presenter();
    }
}
