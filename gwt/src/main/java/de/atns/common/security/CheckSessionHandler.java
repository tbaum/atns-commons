package de.atns.common.security;

import com.google.inject.Inject;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.benutzer.server.RoleServerConverter;
import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.model.UserPresentation;

import java.util.UUID;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class CheckSessionHandler extends DefaultActionHandler<CheckSession, UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private final UserProvider user;
    private final SecurityFilter securityFilter;
    private final UserService userService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public CheckSessionHandler(final UserProvider user, final SecurityFilter securityFilter,
                                       UserService userService) {
        this.user = user;
        this.securityFilter = securityFilter;
        this.userService = userService;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override public final UserPresentation executeInternal(final CheckSession action) {
        return checkSession();
    }

    public UserPresentation checkSession() {
        final SecurityUser user = this.user.get();
        final UUID token = securityFilter.getAuthToken();
        if (user == null || token == null) {
            return UserPresentation.invalidUser();
        } else {
            userService.setActive(user);
            return UserPresentation.nameToken(token.toString(), user.getLogin(),
                    RoleServerConverter.convert(user.getRoles()));
        }
    }
}

