package de.atns.common.security.login.client.gin;

import com.google.gwt.inject.client.GinModules;
import de.atns.common.gwt.client.gin.WidgetPresenterGinjector;
import de.atns.common.security.login.client.LoginPresenter;

/**
 * @author tbaum
 * @since 16.06.2010
 */
@GinModules(LoginModule.class)
public interface LoginInjector extends WidgetPresenterGinjector<LoginPresenter> {
}
