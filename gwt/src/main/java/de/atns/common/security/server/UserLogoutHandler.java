package de.atns.common.security.server;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import de.atns.common.security.SecurityService;
import de.atns.common.security.SecurityUser;
import de.atns.common.security.client.action.UserLogout;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class UserLogoutHandler implements ActionHandler<UserLogout, UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(UserLogoutHandler.class);
    private final SecurityService securityService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public UserLogoutHandler(final SecurityService securityService) {
        this.securityService = securityService;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public Class<UserLogout> getActionType() {
        return UserLogout.class;
    }

    @Override @Transactional
    public final UserPresentation execute(final UserLogout action, final ExecutionContext context) {
        final SecurityUser user = securityService.logout();

        LOG.info("loggedout " + (user != null ? user.getLogin() : ""));
        return new UserPresentation(user != null ? user.getLogin() : null, null);
    }

    @Override
    public void rollback(final UserLogout action, final UserPresentation result, final ExecutionContext context) {
    }
}
