package de.atns.common.security.server;

import com.google.inject.Inject;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.SecurityFilter;
import de.atns.common.security.SecurityUser;
import de.atns.common.security.UserProvider;
import de.atns.common.security.client.action.UserLogin;
import de.atns.common.security.client.model.UserPresentation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class UserLoginHandler extends DefaultActionHandler<UserLogin, UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(UserLoginHandler.class);
    private final SecurityFilter securityFilter;
    private final UserProvider userProvider;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public UserLoginHandler(final UserProvider userProvider, final SecurityFilter securityFilter) {
        super(UserLogin.class);
        this.userProvider = userProvider;
        this.securityFilter = securityFilter;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override public final UserPresentation executeInternal(final UserLogin action) {
        LOG.info("login " + action.getUserName());
        final UUID token = securityFilter.login(action.getUserName(), action.getPassword());
        if (token == null) {
            throw new RuntimeException("invalid login");
        }
        final SecurityUser user = userProvider.get();

        return new UserPresentation(user != null ? user.getLogin() : null, token.toString());
    }
}
