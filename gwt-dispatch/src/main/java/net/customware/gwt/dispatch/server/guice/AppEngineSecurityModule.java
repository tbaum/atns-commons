package net.customware.gwt.dispatch.server.guice;

import com.google.inject.AbstractModule;
import net.customware.gwt.dispatch.server.appengine.AppEngineSecureSessionValidator;
import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;

/**
 * This is a Guice configuration module for projects being configured for
 * security and deployment in the Google App Engine environment.
 *
 * @author David Peterson
 */
public class AppEngineSecurityModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SecureSessionValidator.class).to(AppEngineSecureSessionValidator.class);
    }
}
