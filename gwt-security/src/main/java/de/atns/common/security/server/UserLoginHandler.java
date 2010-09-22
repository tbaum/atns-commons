package de.atns.common.security.server;

import com.google.inject.Inject;
import de.atns.common.security.SecurityFilter;
import de.atns.common.security.SecurityUser;
import de.atns.common.security.UserProvider;
import de.atns.common.security.client.action.UserLogin;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class UserLoginHandler implements ActionHandler<UserLogin, UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(UserLoginHandler.class);
    private final SecurityFilter securityFilter;
    private final UserProvider userProvider;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public UserLoginHandler(final UserProvider userProvider, final SecurityFilter securityFilter) {
        this.userProvider = userProvider;
        this.securityFilter = securityFilter;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public Class<UserLogin> getActionType() {
        return UserLogin.class;
    }

    @Override
    public final UserPresentation execute(final UserLogin action, ExecutionContext context) throws ActionException {
        LOG.info("login " + action.getUserName());
        final UUID token = securityFilter.login(action.getUserName(), action.getPassword());
        if (token == null) {
            throw new RuntimeException("invalid login");
        }
        final SecurityUser user = userProvider.get();

        return new UserPresentation(user != null ? user.getLogin() : null, token.toString());
    }

    @Override
    public void rollback(final UserLogin action, final UserPresentation result, final ExecutionContext context) {
    }
}
