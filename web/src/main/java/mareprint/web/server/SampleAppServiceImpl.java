package mareprint.web.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import mareprint.web.client.SampleAppService;
import mareprint.web.client.model.ServerUploadStatus;
import static mareprint.web.server.UploadServlet.getServerUploadStatus;


public class SampleAppServiceImpl extends RemoteServiceServlet implements SampleAppService {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -3308291967860231238L;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SampleAppServiceAsync ---------------------

    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\" Server answered: \"Hi!\"";
    }

    public ServerUploadStatus getUploadStatus() {
        return getServerUploadStatus(this.getThreadLocalRequest());
    }
}