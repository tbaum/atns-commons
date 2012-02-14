package de.atns.common.security.benutzer;

import de.atns.common.security.benutzer.client.action.*;
import de.atns.common.security.benutzer.client.action.UserDetail;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

/**
 * @author tbaum
 * @since 13.06.2010
 */
public class BenutzerActionHandlerModule extends ActionHandlerModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configureHandlers() {
        bindHandler(UserDetail.class, UserLoadDetailHandler.class);
        bindHandler(BenutzerChangePassword.class, BenutzerChangePasswordHandler.class);
        bindHandler(BenutzerCreate.class, BenutzerCreateHandler.class);
        bindHandler(BenutzerList.class, BenutzerListHandler.class);
        bindHandler(BenutzerUpdate.class, BenutzerUpdateHandler.class);
    }
}
