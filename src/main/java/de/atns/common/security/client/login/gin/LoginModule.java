package de.atns.common.security.client.login.gin;

import de.atns.common.security.client.SharedServicesModule;
import de.atns.common.security.client.login.LoginPresenter;
import de.atns.common.security.client.login.LoginView;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class LoginModule extends SharedServicesModule {

    @Override protected void configure() {
        super.configure();

        bindPresenter(LoginPresenter.class, LoginPresenter.Display.class, LoginView.class);
    }
}
