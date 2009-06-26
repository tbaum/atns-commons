package mareprint.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface SampleAppServiceAsync {
// -------------------------- OTHER METHODS --------------------------

    void getMessage(String msg, AsyncCallback<String> async);
}
