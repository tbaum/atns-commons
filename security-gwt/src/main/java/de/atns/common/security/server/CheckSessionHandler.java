package de.atns.common.security.server;

import com.google.inject.Inject;
import de.atns.common.security.SecurityFilter;
import de.atns.common.security.SecurityUser;
import de.atns.common.security.UserProvider;
import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;

import java.util.UUID;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class CheckSessionHandler implements ActionHandler<CheckSession, UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private final UserProvider user;
    private final SecurityFilter securityFilter;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public CheckSessionHandler(final UserProvider user, final SecurityFilter securityFilter) {
        this.user = user;
        this.securityFilter = securityFilter;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public Class<CheckSession> getActionType() {
        return CheckSession.class;
    }

    @Override
    public final UserPresentation execute(final CheckSession action, ExecutionContext context) throws ActionException {
        final SecurityUser user = this.user.get();
        final UUID token = securityFilter.getAuthToken();

        return new UserPresentation(user != null ? user.getLogin() : null, token != null ? token.toString() : null);
    }

    @Override
    public void rollback(final CheckSession action, final UserPresentation result, final ExecutionContext context) {
    }
}

