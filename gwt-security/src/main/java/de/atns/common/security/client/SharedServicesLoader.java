package de.atns.common.security.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.gin.SharedServices;

/**
 * @author tbaum
 * @since 05.10.2010
 */
@Singleton
public class SharedServicesLoader {
// ------------------------------ FIELDS ------------------------------

    private static SharedServicesInjector injector;
    private final SharedServices sharedServices;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public SharedServicesLoader(SharedServices sharedServices) {
        this.sharedServices = sharedServices;
    }

// -------------------------- OTHER METHODS --------------------------

    public void load(final SharedServiceLoaderCallback callback) {
        GWT.runAsync(SharedServicesLoader.class, new RunAsyncCallback() {
            public void onSuccess() {
                if (injector == null) {
                    injector = GWT.create(SharedServicesInjector.class);
                    injector.sharedServicesAware().setSharedServices(sharedServices);
                }
                callback.onLoaded(injector);
            }

            public void onFailure(Throwable reason) {
                Window.alert("Failed to load shared services");
            }
        });
    }

// -------------------------- INNER CLASSES --------------------------

    public interface SharedServiceLoaderCallback {
        void onLoaded(SharedServicesInjector injector);
    }
}
