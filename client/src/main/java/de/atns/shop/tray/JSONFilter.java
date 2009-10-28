package de.atns.shop.tray;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import de.atns.shop.tray.data.Result;
import org.eclipse.jetty.util.ajax.JSON;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author tbaum
 * @since 30.09.2009 02:41:30
 */
@Singleton public class JSONFilter implements Filter {
// ------------------------------ FIELDS ------------------------------

    private final Provider<Result> result;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public JSONFilter(final Provider<Result> result) {
        this.result = result;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Filter ---------------------

    @Override public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override public void doFilter(final ServletRequest request,
                                   final ServletResponse response,
                                   final FilterChain chain) throws IOException {
        final PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        try {
            //TODO forbid getWriter in response
            chain.doFilter(request, response);
            printWriter.write(JSON.toString(result.get().map()));
        } catch (Exception e) {
            e.printStackTrace();
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            printWriter.write(Util.mapException(e));
        }
    }

    @Override public void destroy() {
    }
}
