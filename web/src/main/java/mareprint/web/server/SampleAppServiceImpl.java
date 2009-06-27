package mareprint.web.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import mareprint.web.client.SampleAppService;


public class SampleAppServiceImpl extends RemoteServiceServlet implements SampleAppService {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SampleAppServiceAsync ---------------------

    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\" Server answered: \"Hi!\"";
    }
}