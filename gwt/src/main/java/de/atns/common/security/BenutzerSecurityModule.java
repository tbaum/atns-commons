package de.atns.common.security;

import com.google.inject.AbstractModule;

/**
 * @author tbaum
 * @since 17.08.2010
 */
public class BenutzerSecurityModule extends SecurityModule {
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
        bind(UserService.class).to(UserServiceImpl.class);

        install(new AbstractModule() {
            @Override protected void configure() {
//                bind(DummyDataCreator.class).asEagerSingleton();
            }
        });

        install(new SecurityActionHandlerModule());
    }
}
