package de.atns.common.gwt.app.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;
import de.atns.common.gwt.client.gin.*;
import de.atns.common.security.benutzer.client.gin.BenutzerLoader;
import de.atns.common.security.client.login.gin.LoginLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author tbaum
 * @since 22.11.10
 */
public abstract class ApplicationActivityMapper implements ActivityMapper, ModuleReadyEventHandler {
// ------------------------------ FIELDS ------------------------------

    @Inject private static ApplicationPresenter application;
    @Inject private static SharedServices sharedServices;
    private final List<WidgetPresenterModuleLoader> moduleLoaders;
    private final Logger LOG = Logger.getLogger(this.getClass().toString());
    private HandlerRegistration handlerRegistration;

// --------------------------- CONSTRUCTORS ---------------------------

    public ApplicationActivityMapper() {
        moduleLoaders = new ArrayList<WidgetPresenterModuleLoader>(loadModules(sharedServices));
        moduleLoaders.add(new LoginLoader(sharedServices));
        moduleLoaders.add(new BenutzerLoader(sharedServices));

        handlerRegistration = sharedServices.eventBus().addHandler(ModuleReadyEventHandler.type, this);
        onReady(null);
    }

    protected abstract List<? extends WidgetPresenterModuleLoader> loadModules(final SharedServices sharedServices);

    @Override public void onReady(SharedServicesModuleLoader sharedServicesModuleLoader) {
        if (getLoadedCount() == moduleLoaders.size()) {
            handlerRegistration.removeHandler();
            LOG.info("fire ApplicationReadyEvent()");
            sharedServices.eventBus().fireEvent(new ApplicationReadyEvent());
        }
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActivityMapper ---------------------

    @Override
    public Activity getActivity(final Place place) {
        for (final WidgetPresenterModuleLoader moduleLoader : moduleLoaders) {
            if (!moduleLoader.canHandlePlace(place)) {
                continue;
            }

            if (!moduleLoader.isLoaded()) {
                Window.alert("Modul " + moduleLoader.getClass() + " noch nicht geladen");
                continue;
            }

            final PlacePresenter presenter = (PlacePresenter) moduleLoader.presenter();
            return presenter.updateForPlace(place);
        }

        return null;
    }

// -------------------------- OTHER METHODS --------------------------

    private int getLoadedCount() {
        int loaded = 0;
        for (WidgetPresenterModuleLoader moduleLoader : moduleLoaders) {
            if (moduleLoader.isLoaded()) loaded++;
        }
        return loaded;
    }
}
