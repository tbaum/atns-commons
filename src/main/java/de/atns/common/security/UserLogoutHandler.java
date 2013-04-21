package de.atns.common.security;

import com.google.inject.Inject;
import com.google.inject.extensions.security.SecurityFilter;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.client.action.UserLogout;
import de.atns.common.security.client.model.UserPresentation;

import static de.atns.common.security.client.model.UserPresentation.invalidUser;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class UserLogoutHandler extends DefaultActionHandler<UserLogout, UserPresentation> {

    private final SecurityFilter securityFilter;

    @Inject public UserLogoutHandler(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Override public final UserPresentation executeInternal(final UserLogout action) {
        securityFilter.logout();
        return invalidUser();
    }
}
