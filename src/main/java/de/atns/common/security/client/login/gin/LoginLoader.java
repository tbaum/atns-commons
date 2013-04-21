package de.atns.common.security.client.login.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.gwt.client.gin.WidgetPresenterGinjector;
import de.atns.common.gwt.client.gin.WidgetPresenterModuleLoader;
import de.atns.common.security.client.login.LoginPlace;
import de.atns.common.security.client.login.LoginPresenter;
import de.atns.common.security.client.login.LogoutPlace;

/**
 * @author tbaum
 * @since 16.06.2010
 */
@Singleton
public class LoginLoader extends WidgetPresenterModuleLoader<LoginPresenter> {

    @Inject public LoginLoader(final SharedServices sharedServices) {
        super(sharedServices);
    }

    @Override public boolean canHandlePlace(final Place place) {
        return place instanceof LoginPlace || place instanceof LogoutPlace;
    }

    @Override protected WidgetPresenterGinjector<LoginPresenter> create() {
        return GWT.create(LoginInjector.class);
    }

    @Override public void load() {
        GWT.runAsync(LoginLoader.class, this);
    }
}
