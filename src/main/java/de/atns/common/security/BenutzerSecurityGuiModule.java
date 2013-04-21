package de.atns.common.security;

import de.atns.common.security.benutzer.BenutzerActionHandlerModule;

/**
 * @author tbaum
 * @since 17.08.2010
 */
public class BenutzerSecurityGuiModule extends BenutzerSecurityModule {

    @Override protected void configureSecurity() {
        super.configureSecurity();
        install(new BenutzerActionHandlerModule());
    }
}
