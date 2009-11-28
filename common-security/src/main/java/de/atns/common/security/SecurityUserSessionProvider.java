package de.atns.common.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.atns.common.security.client.SecurityUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static de.atns.common.security.AuthenticateFilter.SESSION_ATTR_NAME;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class SecurityUserSessionProvider implements Provider<SecurityUser> {
// ------------------------------ FIELDS ------------------------------

    private final Provider<HttpServletRequest> request;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public SecurityUserSessionProvider(final Provider<HttpServletRequest> request) {
        this.request = request;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Provider ---------------------

    @Override public SecurityUser get() {
        final HttpServletRequest servletRequest = request.get();
        final HttpSession session = servletRequest.getSession(false);
        if (session == null) {
            throw new NotLogginException();
        }
        SecurityUser user = (SecurityUser) session.getAttribute(SESSION_ATTR_NAME);

        if (user == null) {
            throw new NotLogginException();
        }

        return user;
    }
}
