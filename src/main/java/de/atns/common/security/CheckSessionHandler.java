package de.atns.common.security;

import com.google.inject.Inject;
import com.google.inject.extensions.security.SecurityService;
import com.google.inject.extensions.security.SecurityUser;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.model.UserPresentation;

import static de.atns.common.security.benutzer.server.RoleServerConverter.convert;
import static de.atns.common.security.client.model.UserPresentation.invalidUser;
import static de.atns.common.security.client.model.UserPresentation.nameToken;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class CheckSessionHandler extends DefaultActionHandler<CheckSession, UserPresentation> {

    private final SecurityService securityService;

    @Inject public CheckSessionHandler(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override public final UserPresentation executeInternal(final CheckSession action) {
        return checkSession();
    }

    public UserPresentation checkSession() {
        final SecurityUser user = securityService.currentUser();
        return user == null ? invalidUser() : nameToken(user.getToken(), user.getLogin(), convert(user.getRoles()));
    }
}

