package de.atns.common.security;

import com.google.inject.Inject;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.client.action.UserLogout;
import de.atns.common.security.client.model.UserPresentation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class UserLogoutHandler extends DefaultActionHandler<UserLogout, UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(UserLogoutHandler.class);
    private final SecurityService securityService;
    private final UserService userService;


// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public UserLogoutHandler(final SecurityService securityService, UserService userService) {
        super();
        this.securityService = securityService;
        this.userService = userService;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override public final UserPresentation executeInternal(final UserLogout action) {
        SecurityUser user = securityService.currentUser();
        userService.setInactive(user);
        user = securityService.logout();
        LOG.info("loggedout " + (user != null ? user.getLogin() : ""));
        return UserPresentation.invalidUser();
    }
}
