package net.customware.gwt.dispatch.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import net.customware.gwt.dispatch.client.appengine.AppEngineSecureSessionAccessor;
import net.customware.gwt.dispatch.client.secure.SecureSessionAccessor;

/**
 * Configures the application to use Google App Engine security for
 * authentication.
 *
 * @author David Peterson
 */
public class AppEngineSecurityModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(SecureSessionAccessor.class).to(AppEngineSecureSessionAccessor.class);
    }
}
