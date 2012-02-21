package de.atns.common.security;

import com.google.inject.Inject;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.client.action.LoadUserDetail;
import de.atns.common.security.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 24.06.2010
 */
public abstract class AbstractLoadUserDetailHandler<A extends LoadUserDetail<R>, R extends UserPresentation>
        extends DefaultActionHandler<A, R> {
// ------------------------------ FIELDS ------------------------------

    private final SecurityService securityService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public AbstractLoadUserDetailHandler(SecurityService securityService) {
        this.securityService = securityService;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    @Secured
    public R executeInternal(A action) {
        final SecurityUser securityUser = securityService.currentUser();
        return loadUserDetail(securityUser);
    }

    protected abstract R loadUserDetail(SecurityUser securityUser);
}