package de.atns.common.security;

import com.google.inject.AbstractModule;
import com.google.inject.extensions.security.SecurityModule;
import com.google.inject.extensions.security.UserService;
import de.atns.common.security.benutzer.UserServiceImpl;

/**
 * @author tbaum
 * @since 17.08.2010
 */
public class BenutzerSecurityModule extends SecurityModule {

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
