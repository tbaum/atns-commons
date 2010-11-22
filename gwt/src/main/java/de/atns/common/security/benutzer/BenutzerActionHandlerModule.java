package de.atns.common.security.benutzer;

import de.atns.common.security.benutzer.client.action.BenutzerChangePassword;
import de.atns.common.security.benutzer.client.action.BenutzerCreate;
import de.atns.common.security.benutzer.client.action.BenutzerList;
import de.atns.common.security.benutzer.client.action.BenutzerUpdate;
import de.atns.common.security.benutzer.server.BenutzerChangePasswordHandler;
import de.atns.common.security.benutzer.server.BenutzerCreateHandler;
import de.atns.common.security.benutzer.server.BenutzerListHandler;
import de.atns.common.security.benutzer.server.BenutzerUpdateHandler;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

/**
 * @author tbaum
 * @since 13.06.2010
 */
public class BenutzerActionHandlerModule extends ActionHandlerModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configureHandlers() {
        bindHandler(BenutzerChangePassword.class, BenutzerChangePasswordHandler.class);
        bindHandler(BenutzerCreate.class, BenutzerCreateHandler.class);
        bindHandler(BenutzerList.class, BenutzerListHandler.class);
        bindHandler(BenutzerUpdate.class, BenutzerUpdateHandler.class);
    }
}
