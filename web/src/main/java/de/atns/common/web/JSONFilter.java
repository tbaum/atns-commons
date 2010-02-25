package de.atns.common.web;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.util.ajax.JSON;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tbaum
 * @since 30.09.2009
 */
@Singleton public class JSONFilter implements Filter {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(JSONFilter.class);
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
            printWriter.write(result.get().toJSON());
        } catch (Exception e) {
            LOG.error(e, e);
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            printWriter.write(mapException(e));
        }
    }

    @Override public void destroy() {
    }

// -------------------------- OTHER METHODS --------------------------

    private String mapException(final Throwable e) {
        final Map<String, Serializable> result = new LinkedHashMap<String, Serializable>();
        result.put("failed", true);
        result.put("message", e.getMessage());
        result.put("stacktrace", e.getStackTrace());

        if (e.getCause() != null) {
            result.put("cause", mapException(e.getCause()));
        }

        return JSON.toString(result);
    }
}
