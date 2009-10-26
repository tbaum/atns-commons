package mareprint.web.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.String.format;

/**
 * @author tbaum
 * @since 27.06.2009 03:08:28
 */
public class SessionIdServlet extends HttpServlet {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -1363549786105298384L;

// -------------------------- OTHER METHODS --------------------------

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        /*
        String baseUrl = request.getScheme() + "://" + request.getServerName();

        if ((request.getScheme().equals("http") && request.getServerPort() != 80) ||
                (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            baseUrl += ":" + request.getServerPort();
        }
        baseUrl += request.getContextPath();
        */


        final PrintWriter printWriter = response.getWriter();
        final String content = format("config={sessionId:'%s'}", session.getId());

        printWriter.println(content);
        response.setContentLength(content.length());
        response.setStatus(SC_OK);
    }
}