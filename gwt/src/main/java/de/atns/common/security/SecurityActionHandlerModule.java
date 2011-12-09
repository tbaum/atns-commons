package de.atns.common.security;

import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.action.UserDetail;
import de.atns.common.security.client.action.UserLogin;
import de.atns.common.security.client.action.UserLogout;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

/**
 * @author tbaum
 * @since 13.06.2010
 */
public class SecurityActionHandlerModule extends ActionHandlerModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configureHandlers() {
        bindHandler(CheckSession.class, CheckSessionHandler.class);
        bindHandler(UserDetail.class, UserLoadDetailHandler.class);
        bindHandler(UserLogin.class, UserLoginHandler.class);
        bindHandler(UserLogout.class, UserLogoutHandler.class);
    }
}
