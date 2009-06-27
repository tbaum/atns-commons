package mareprint.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import mareprint.web.client.model.ServerUploadStatus;


public interface SampleAppServiceAsync {
// -------------------------- OTHER METHODS --------------------------

    void getMessage(String msg, final AsyncCallback<String> async);

    void getUploadStatus(final AsyncCallback<ServerUploadStatus> async);
}
