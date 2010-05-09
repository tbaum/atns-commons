package de.atns.common.web;

import com.google.inject.Singleton;

import javax.servlet.*;
import java.io.IOException;

@Singleton
public class EncodingFilter implements Filter {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Filter ---------------------

    @Override public void init(final FilterConfig config1) {
    }

    @Override public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override public void destroy() {
    }
}

