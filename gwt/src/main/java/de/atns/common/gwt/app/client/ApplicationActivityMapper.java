package de.atns.common.gwt.app.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import de.atns.common.gwt.client.gin.ModuleLoader;
import de.atns.common.gwt.client.gin.PlacePresenter;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.security.benutzer.client.gin.BenutzerLoader;
import de.atns.common.security.login.client.gin.LoginLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tbaum
 * @since 22.11.10
 */
public abstract class ApplicationActivityMapper implements ActivityMapper {
// ------------------------------ FIELDS ------------------------------

    @Inject private static ApplicationPresenter application;
    @Inject private static SharedServices sharedServices;
    private final List<ModuleLoader> moduleLoaders;

// --------------------------- CONSTRUCTORS ---------------------------

    public ApplicationActivityMapper() {
        // sharedServices = applicationInjector.sharedServices();

        moduleLoaders = new ArrayList<ModuleLoader>(loadModules(sharedServices));

        moduleLoaders.add(new LoginLoader(sharedServices));
        moduleLoaders.add(new BenutzerLoader(sharedServices));
    }

    protected abstract List<? extends ModuleLoader> loadModules(final SharedServices sharedServices);

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActivityMapper ---------------------

    @Override
    public Activity getActivity(final Place place) {
        for (final ModuleLoader moduleLoader : moduleLoaders) {
            if (!moduleLoader.canHandlePlace(place)) {
                continue;
            }

            if (!moduleLoader.isLoaded()) {
                //TODO show wait-dialog
                System.err.println("modul noch nicht geladen");
                continue;
            }

            final PlacePresenter presenter = (PlacePresenter) moduleLoader.presenter();
            return presenter.updateForPlace(place);
        }

        return null;
    }
}
