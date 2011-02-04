package de.atns.common.security.benutzer.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.gin.WidgetPresenterModuleLoader;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.gwt.client.gin.WidgetPresenterGinjector;
import de.atns.common.security.benutzer.client.BenutzerPlace;
import de.atns.common.security.benutzer.client.BenutzerPresenter;

/**
 * @author tbaum
 * @since 16.06.2010
 */
@Singleton
public class BenutzerLoader extends WidgetPresenterModuleLoader<BenutzerPresenter> {
// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerLoader(final SharedServices sharedServices) {
        super(sharedServices);
    }

// -------------------------- OTHER METHODS --------------------------

    @Override public boolean canHandlePlace(final Place place) {
        return place instanceof BenutzerPlace;
    }

    @Override protected WidgetPresenterGinjector<BenutzerPresenter> create() {
        return GWT.create(BenutzerInjector.class);
    }

    public void load() {
        GWT.runAsync(BenutzerLoader.class, this);
    }
}
