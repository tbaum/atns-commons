package de.atns.common.web;

import com.google.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tbaum
 * @since 21.10.2009
 */
public class Forwarder {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Inject public Forwarder(final HttpServletRequest request, final HttpServletResponse response) {
        this.response = response;
        this.request = request;
    }

    public void forward(String target) throws ServletException, IOException {
        final String ctxPath = request.getContextPath();
        if (target.startsWith(ctxPath)) {
            target = target.substring(ctxPath.length());
        }
        request.getRequestDispatcher(target).forward(request, response);
    }

    public void redirect(final String s) throws IOException {
        final String ctxPath = request.getContextPath();
        if (s.startsWith(ctxPath)) {
            response.sendRedirect(s);
        } else {
            response.sendRedirect(ctxPath + s);
        }
    }
}
