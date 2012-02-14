package de.atns.common.security;

import de.atns.common.security.benutzer.BenutzerActionHandlerModule;

/**
 * @author tbaum
 * @since 17.08.2010
 */
public class BenutzerSecurityGuiModule extends BenutzerSecurityModule {
// -------------------------- OTHER METHODS --------------------------

//
//    @Override protected void configure() {
//        install(new SecurityModule() {
//            @Override protected void configureSecurity() {
//                bind(UserService.class).to(UserServiceImpl.class);
//            }
//        });

//    }

    @Override protected void configureSecurity() {
        super.configureSecurity();
        install(new BenutzerActionHandlerModule());
    }
}
