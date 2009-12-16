package de.atns.common.security;

import com.google.inject.AbstractModule;
import de.atns.common.security.client.Secured;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class SecurityModule extends AbstractModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configure() {
        SecurityScope securityScope = new SecurityScope();
        bindScope(SecurityScoped.class, securityScope);
        bind(SecurityScope.class).toInstance(securityScope);
        final SecurityInterceptor securityInterceptor = new SecurityInterceptor(securityScope);
        bindInterceptor(any(), annotatedWith(Secured.class), securityInterceptor);
    }
}