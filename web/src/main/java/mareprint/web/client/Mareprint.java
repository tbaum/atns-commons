package mareprint.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * @author tbaum
 * @since 26.06.2009
 */
public class Mareprint implements EntryPoint {

    public void onModuleLoad() {
        final Button button = new Button("Click me");

        final Label label = new Label();

        button.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {

                if (label.getText().equals("")) {

                    SampleAppService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));

                } else {

                    label.setText("");

                }

            }

        });

        // Assume that the host HTML has elements defined whose

        // IDs are "slot1", "slot2".  In a real app, you probably would not want

        // to hard-code IDs.  Instead, you could, for example, search for all

        // elements with a particular CSS class and replace them with widgets.

        //

        RootPanel.get("slot1").add(button);

        RootPanel.get("slot2").add(label);

        // remove the loading message from the browser

        com.google.gwt.user.client.Element loading = DOM.getElementById("loading");

        DOM.removeChild(RootPanel.getBodyElement(), loading);
    }

    static class MyAsyncCallback implements AsyncCallback {

        public void onSuccess(Object object) {

            DOM.setInnerHTML(label.getElement(), (String) object);

        }

        public void onFailure(Throwable throwable) {

            label.setText("Failed to receive answer from server!");

        }

        Label label;

        public MyAsyncCallback(Label label) {

            this.label = label;

        }

    }
}

