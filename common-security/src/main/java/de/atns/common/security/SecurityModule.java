package de.atns.common.security;

import com.google.inject.AbstractModule;
import de.atns.common.security.client.Secured;
import de.atns.common.security.client.SecurityUser;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class SecurityModule extends AbstractModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configure() {
        SecurityScope batchScope = new SecurityScope();
        bindScope(SecurityScoped.class, batchScope);
        bind(SecurityScope.class).toInstance(batchScope);

        final SecurityInterceptor securityInterceptor = new SecurityInterceptor();
        requestInjection(securityInterceptor);
        bindInterceptor(any(), annotatedWith(Secured.class), securityInterceptor);

        bind(SecurityUser.class).toProvider(SecurityUserSessionProvider.class);
    }
}