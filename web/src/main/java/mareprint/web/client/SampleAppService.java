package mareprint.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import mareprint.web.client.model.ServerUploadStatus;

@RemoteServiceRelativePath("SampleAppService")
public interface SampleAppService extends RemoteService {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SampleAppServiceAsync ---------------------

    String getMessage(String msg);

    ServerUploadStatus getUploadStatus();
}
