package de.atns.common.security.server;

import com.google.inject.Inject;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.SecurityFilter;
import de.atns.common.security.SecurityService;
import de.atns.common.security.UserService;
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
    private final CheckSessionHandler checkSessionHandler;
    private final UserService userService;
    private final SecurityService securityService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public UserLoginHandler(final SecurityFilter securityFilter, CheckSessionHandler checkSessionHandler,
                                    UserService userService, SecurityService securityService) {
        super(UserLogin.class);
        this.securityFilter = securityFilter;
        this.checkSessionHandler = checkSessionHandler;
        this.userService = userService;
        this.securityService = securityService;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override public final UserPresentation executeInternal(final UserLogin action) {
        LOG.info("login " + action.getUserName());
        final UUID token = securityFilter.login(action.getUserName(), action.getPassword());
        if (token == null) {
            throw new RuntimeException("invalid login");
        }
        userService.successfullLogin(securityService.currentUser());

        return checkSessionHandler.checkSession();
    }
}
