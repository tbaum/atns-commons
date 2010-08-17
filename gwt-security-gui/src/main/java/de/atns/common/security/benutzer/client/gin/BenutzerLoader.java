package de.atns.common.security.benutzer.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.gin.ModuleLoader;
import de.atns.common.gwt.client.gin.ModuleLoaderCallback;
import de.atns.common.gwt.client.gin.SharedServices;

/**
 * @author tbaum
 * @since 16.06.2010
 */
@Singleton
public class BenutzerLoader implements ModuleLoader {
// ------------------------------ FIELDS ------------------------------

    private static BenutzerInjector injector;
    private final SharedServices sharedServices;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerLoader(SharedServices sharedServices) {
        this.sharedServices = sharedServices;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ModuleLoader ---------------------

    public void load(final ModuleLoaderCallback callback) {
        GWT.runAsync(BenutzerLoader.class, new RunAsyncCallback() {
            public void onSuccess() {
                if (injector == null) {
                    injector = GWT.create(BenutzerInjector.class);
                    injector.sharedServicesAware().setSharedServices(sharedServices);
                }

                callback.onLoaded(injector.presenter());
            }

            public void onFailure(Throwable reason) {
                Window.alert("Failed to load admin presenter");
            }
        });
    }
}
