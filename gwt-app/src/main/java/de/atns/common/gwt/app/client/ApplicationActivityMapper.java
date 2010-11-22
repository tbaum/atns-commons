package de.atns.common.gwt.app.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import de.atns.common.gwt.client.gin.ModuleLoader;
import de.atns.common.gwt.client.gin.PlacePresenter;
import de.atns.common.gwt.client.gin.SharedServices;

import java.util.List;

/**
 * @author tbaum
 * @since 22.11.10
 */
public abstract class ApplicationActivityMapper implements ActivityMapper {
// ------------------------------ FIELDS ------------------------------

    private final List<? extends ModuleLoader> moduleLoaders;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public ApplicationActivityMapper(ApplicationInjector applicationInjector) {
        moduleLoaders = loadModules(applicationInjector.sharedServices());
    }

    protected abstract List<? extends ModuleLoader> loadModules(final SharedServices sharedServices);

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActivityMapper ---------------------

    @Override
    public Activity getActivity(Place place) {
        for (ModuleLoader moduleLoader : moduleLoaders) {
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
