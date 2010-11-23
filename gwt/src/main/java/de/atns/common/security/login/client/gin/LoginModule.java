package de.atns.common.security.login.client.gin;

import de.atns.common.security.client.SharedServicesModule;
import de.atns.common.security.login.client.LoginPresenter;
import de.atns.common.security.login.client.LoginView;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class LoginModule extends SharedServicesModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configure() {
        super.configure();

        bindPresenter(LoginPresenter.class, LoginPresenter.Display.class, LoginView.class);
    }
}
