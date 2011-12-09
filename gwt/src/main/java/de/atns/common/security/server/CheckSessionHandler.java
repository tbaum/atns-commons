package de.atns.common.security.server;

import com.google.inject.Inject;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.SecurityFilter;
import de.atns.common.security.SecurityUser;
import de.atns.common.security.UserProvider;
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

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public CheckSessionHandler(final UserProvider user, final SecurityFilter securityFilter) {
        super(CheckSession.class);
        this.user = user;
        this.securityFilter = securityFilter;
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
            return UserPresentation.nameToken(token.toString(), user.getLogin(),
                    RoleServerConverter.convert(user.getRoles()));
        }
    }
}

