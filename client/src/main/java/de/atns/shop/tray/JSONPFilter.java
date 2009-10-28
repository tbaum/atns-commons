package de.atns.shop.tray;

import com.google.inject.Singleton;
import org.eclipse.jetty.util.ajax.JSON;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author tbaum
 * @since 30.09.2009 02:41:30
 */
@Singleton public class JSONPFilter implements Filter {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Filter ---------------------

    @Override public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override public void doFilter(final ServletRequest request,
                                   final ServletResponse response,
                                   final FilterChain chain) throws IOException, ServletException {
        response.setContentType("application/javascript");
        final int[] status = new int[]{SC_OK};
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final PrintWriter printWriter = new PrintWriter(bos);
        chain.doFilter(request, new HttpServletResponseWrapper((HttpServletResponse) response) {
            @Override public PrintWriter getWriter() throws IOException {
                return printWriter;
            }

            @Override public void setStatus(final int sc) {
                status[0] = sc;
            }
        });

        printWriter.close();
        final String function = status[0] == SC_OK ? "jsonResponse" : "jsonErrorResponse";

        ((HttpServletResponse) response).setStatus(SC_OK);
        response.getWriter().write(function + "(" + JSON.toString(new String(bos.toByteArray())) + ");");
    }

    @Override public void destroy() {
    }
}