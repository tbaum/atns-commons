package de.atns.common.gwt.client.gin;

import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import de.atns.common.gwt.client.WidgetPresenter;

/**
 * @author tbaum
 * @since 18.11.10
 */
public abstract class ModuleLoader<T extends WidgetPresenter & PlacePresenter> implements RunAsyncCallback {
// ------------------------------ FIELDS ------------------------------

    private WidgetPresenterGinjector<T> injector;

    private T presenter;
    private final SharedServices sharedServices;

// --------------------------- CONSTRUCTORS ---------------------------

    public ModuleLoader(SharedServices sharedServices) {
        this.sharedServices = sharedServices;
        load();
    }

    protected abstract void load();

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface RunAsyncCallback ---------------------

    public void onFailure(Throwable reason) {
        Window.alert("Failed to load module");
    }

    public void onSuccess() {
        if (injector == null) {
            injector = create();
            injector.sharedServicesAware().setSharedServices(sharedServices);
        }
        presenter = injector.presenter();
    }

// -------------------------- OTHER METHODS --------------------------

    public abstract boolean canHandlePlace(Place place);

    protected abstract WidgetPresenterGinjector<T> create();

    public WidgetPresenterGinjector<T> injector() {
        return injector;
    }

    public boolean isLoaded() {
        return presenter != null;
    }

    public T presenter() {
        return presenter;
    }
}
