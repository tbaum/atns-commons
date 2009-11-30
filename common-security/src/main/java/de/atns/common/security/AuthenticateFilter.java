package de.atns.common.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import de.atns.common.security.client.SecurityUser;
import de.atns.common.security.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author tbaum
 * @since 21.10.2009
 */
@Singleton
public class AuthenticateFilter implements Filter {
// ------------------------------ FIELDS ------------------------------

    static final String SESSION_ATTR_NAME = "_SECURITY_USER";
    private final Provider<UserService> userService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public AuthenticateFilter(final Provider<UserService> userService) {
        this.userService = userService;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Filter ---------------------

    @Override public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override public void doFilter(final ServletRequest request,
                                   final ServletResponse response,
                                   final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        final String path = req.getRequestURI();

        if (path.contains("_security_login")) {
            final HttpSession session = req.getSession(true);
            SecurityUser user = userService.get().findUser(req.getParameter("login"), req.getParameter("password"));
            session.setAttribute(SESSION_ATTR_NAME, user);
        } else if (path.contains("_security_logout")) {
            final HttpSession session = req.getSession(true);
            session.removeAttribute(SESSION_ATTR_NAME);
        }
        chain.doFilter(request, response);
    }

    @Override public void destroy() {
    }
}