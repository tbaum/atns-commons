package de.atns.common.web;

import com.google.inject.Singleton;
import org.json.JSONException;
import org.json.JSONStringer;
import org.json.JSONTokener;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author tbaum
 * @since 30.09.2009
 */
@Singleton public class JSONPFilter implements Filter {

    @Override public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override public void doFilter(final ServletRequest request, final ServletResponse response,
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
        final String function = status[0] == SC_OK ? "ok" : "error";

        ((HttpServletResponse) response).setStatus(SC_OK);
        try {
            JSONStringer json = new JSONStringer();
            String result = new String(bos.toByteArray());
            json.object()
                    .key("status").value(function);
            try {
                json.key("result").value(new JSONTokener(result).nextValue());
            } catch (JSONException e) {
                json.key("result").value(result);
            }
            json.endObject();

            response.getWriter().write(request.getParameter("callback") + "(" + json.toString() + ");");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void destroy() {
    }
}
