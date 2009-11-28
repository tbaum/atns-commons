package de.atns.common.security;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.security.client.SecurityUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static de.atns.common.security.AuthenticateFilter.SESSION_ATTR_NAME;

/**
 * @author tbaum
 * @since 30.09.2009
 */
@Singleton public class SecurityFilter implements Filter {
// ------------------------------ FIELDS ------------------------------

    private final SecurityScope securityScope;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public SecurityFilter(final SecurityScope securityScope) {
        this.securityScope = securityScope;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Filter ---------------------

    @Override public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override public void doFilter(final ServletRequest request,
                                   final ServletResponse response,
                                   final FilterChain chain) throws IOException, ServletException {
        securityScope.enter();
        try {
            final HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session != null) {
                SecurityUser user = (SecurityUser) session.getAttribute(SESSION_ATTR_NAME);
                if (user != null) {
                    securityScope.put(SecurityUser.class, user);
                }
            }
            try {
                chain.doFilter(request, response);
            } catch (NotInRoleException e) {
                /// TODO redirect-handler
                throw new ServletException(e);
            } catch (NotLogginException e) {
                /// TODO redirect-handler               
                throw new ServletException(e);
            }
        } finally {
            securityScope.exit();
        }
    }

    @Override public void destroy() {
    }
}