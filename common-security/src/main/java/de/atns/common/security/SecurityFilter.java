package de.atns.common.security;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author tbaum
 * @since 30.09.2009
 */
@Singleton public class SecurityFilter implements Filter {
// ------------------------------ FIELDS ------------------------------

    private final SecurityScope securityScope;
    private final SecurityService securityService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public SecurityFilter(final SecurityScope securityScope, final SecurityService securityService) {
        this.securityScope = securityScope;
        this.securityService = securityService;
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
            securityService.authFromHeader(request);
            securityService.authFromSession(request);

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