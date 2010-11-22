package de.atns.common.security.login.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.gin.ModuleLoader;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.gwt.client.gin.WidgetPresenterGinjector;
import de.atns.common.security.login.client.LoginPlace;
import de.atns.common.security.login.client.LoginPresenter;
import de.atns.common.security.login.client.LogoutPlace;

/**
 * @author tbaum
 * @since 16.06.2010
 */
@Singleton
public class LoginLoader extends ModuleLoader<LoginPresenter> {
// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public LoginLoader(SharedServices sharedServices) {
        super(sharedServices);
    }

// -------------------------- OTHER METHODS --------------------------

    @Override public boolean canHandlePlace(Place place) {
        return place instanceof LoginPlace || place instanceof LogoutPlace;
    }

    @Override protected WidgetPresenterGinjector<LoginPresenter> create() {
        return GWT.create(LoginInjector.class);
    }

    public void load() {
        GWT.runAsync(LoginLoader.class, this);
    }
}
