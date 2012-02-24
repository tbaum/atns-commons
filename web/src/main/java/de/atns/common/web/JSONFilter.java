package de.atns.common.web;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONStringer;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author tbaum
 * @since 30.09.2009
 */
@Singleton public class JSONFilter implements Filter {

    private static final Log LOG = LogFactory.getLog(JSONFilter.class);
    private final Provider<Result> result;

    @Inject public JSONFilter(final Provider<Result> result) {
        this.result = result;
    }

    @Override public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override public void doFilter(final ServletRequest request, final ServletResponse response,
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

    private String mapException(final Throwable e) {
        try {
            final JSONStringer json = new JSONStringer();
            json.object()
                    .key("failed").value(true)
                    .key("type").value(e.getClass())
                    .key("message").value(e.getMessage())
                    .key("stacktrace").value(e.getStackTrace());

            if (e.getCause() != null) {
                json.key("cause").value(mapException(e.getCause()));
            }
            json.endObject();

            return json.toString();
        } catch (JSONException e1) {
            throw new RuntimeException(e1);
        }
    }
}
