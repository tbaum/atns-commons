package de.atns.common.security.server;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.SecurityService;
import de.atns.common.security.SecurityUser;
import de.atns.common.security.UserService;
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
        super(UserLogout.class);
        this.securityService = securityService;
        this.userService = userService;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override @Transactional
    public final UserPresentation executeInternal(final UserLogout action) {
        final SecurityUser user = securityService.logout();
        userService.setInactive(user);
        LOG.info("loggedout " + (user != null ? user.getLogin() : ""));
        return UserPresentation.invalidUser();
    }
}
