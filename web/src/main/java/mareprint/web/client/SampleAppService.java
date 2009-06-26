package mareprint.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


public interface SampleAppService extends RemoteService {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SampleAppServiceAsync ---------------------

    String getMessage(String msg);

// -------------------------- INNER CLASSES --------------------------

    /**
     * Utility/Convenience class.
     * Use SampleAppService.App.getInstance () to access static instance of SampleAppServiceAsync
     */
    public static class App {
        private static SampleAppServiceAsync app = null;

        public static synchronized SampleAppServiceAsync getInstance() {
            if (app == null) {
                app = (SampleAppServiceAsync) GWT.create(SampleAppService.class);
                ((ServiceDefTarget) app).setServiceEntryPoint(GWT.getModuleBaseURL() + "sampleApp.SampleApp/SampleAppService");
            }
            return app;
        }
    }
}
