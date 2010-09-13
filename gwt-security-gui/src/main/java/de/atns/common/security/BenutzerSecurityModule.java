package de.atns.common.security;

import com.google.inject.AbstractModule;
import de.atns.common.security.benutzer.BenutzerActionHandlerModule;
import de.atns.common.security.server.DummyDataCreator;
import de.atns.common.security.server.UserServiceImpl;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

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
                bind(DummyDataCreator.class).asEagerSingleton();
            }
        });

        install(new ActionHandlerModule() {
            @Override protected void configureHandlers() {
                install(new SecurityActionHandlerModule());
                install(new BenutzerActionHandlerModule());

            }
        });

    }
}