package de.atns.common.gwt;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.inject.Singleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tbaum
 * @since 30.09.2009
 */
@Singleton public class GwtSerializationFilter implements Filter {

    private static final Log LOG = LogFactory.getLog(GwtSerializationFilter.class);

    @Override public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override public void doFilter(final ServletRequest request, final ServletResponse response,
                                   final FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            LOG.error("got exception " + e.getMessage() + " " + e.getClass());
            if (e instanceof SerializationException) {
                if (response instanceof HttpServletResponse) {
                    LOG.error("sending 505");
                    ((HttpServletResponse) response).setStatus(505);
                }
            }
        }
    }

    @Override public void destroy() {
    }
}
