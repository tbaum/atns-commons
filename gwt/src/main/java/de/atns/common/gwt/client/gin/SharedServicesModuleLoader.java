package de.atns.common.gwt.client.gin;

import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;

import java.util.logging.Logger;

/**
 * @author tbaum
 * @since 04.02.11
 */
public abstract class SharedServicesModuleLoader<I extends SharedServicesGinjector> implements RunAsyncCallback {
// ------------------------------ FIELDS ------------------------------

    private I injector;
    private final SharedServices sharedServices;
    private final Logger LOG = Logger.getLogger(this.getClass().toString());

// --------------------------- CONSTRUCTORS ---------------------------

    public SharedServicesModuleLoader(final SharedServices sharedServices) {
        this.sharedServices = sharedServices;
        load();
    }

    protected abstract void load();

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface RunAsyncCallback ---------------------

    @Override public void onFailure(final Throwable reason) {
        Window.alert("Failed to load module");
    }

    @Override public void onSuccess() {
        if (injector == null) {
            injector = create();
            injector.sharedServicesAware().setSharedServices(sharedServices);
            LOG.info("fire  ModuleReadyEvent / " + this.getClass());
            sharedServices.eventBus().fireEvent(new ModuleReadyEvent(this));
        }
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract I create();

    public I injector() {
        return injector;
    }

    public boolean isLoaded() {
        return injector != null;
    }
}
