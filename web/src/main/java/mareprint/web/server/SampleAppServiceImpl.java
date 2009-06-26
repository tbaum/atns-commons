package mareprint.web.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import mareprint.web.client.SampleAppService;

/**
 * SampleAppServiceImpl
 *
 * @author Nazmul Idris
 * @version 1.0
 * @since Jan 8, 2008, 10:57:26 PM
 */
public class SampleAppServiceImpl extends RemoteServiceServlet implements SampleAppService {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SampleAppServiceAsync ---------------------

    // Implementation of sample interface method

    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}