package mareprint.web.server;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author tbaum
 * @since 28.06.2009 17:01:19
 */
public class SessionListner implements HttpSessionListener {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HttpSessionListener ---------------------

    public void sessionCreated(HttpSessionEvent se) {
        System.err.println("session created " + se.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        System.err.println("session destroyed " + se.getSession().getId());
    }
}
