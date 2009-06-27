package mareprint.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SampleAppService")
public interface SampleAppService extends RemoteService {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SampleAppServiceAsync ---------------------

    String getMessage(String msg);
}
