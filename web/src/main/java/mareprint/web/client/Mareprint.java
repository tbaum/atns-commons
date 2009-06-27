package mareprint.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import static com.google.gwt.user.client.ui.RootPanel.get;
import static com.google.gwt.user.client.ui.RootPanel.getBodyElement;

/**
 * @author tbaum
 * @since 26.06.2009
 */
public class Mareprint implements EntryPoint {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
        final Button button = new Button("Click me!!");
        final Label label = new Label();

        final SampleAppServiceAsync app = (SampleAppServiceAsync) GWT.create(SampleAppService.class);

        button.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent clickEvent) {
                if (label.getText().equals("")) {
                    app.getMessage("Hello, World!", new AsyncCallback<String>() {
                        public void onSuccess(String s) {
                            label.setText(s);
                        }

                        public void onFailure(Throwable throwable) {
                            label.setText("Failed to receive answer from server!");
                        }
                    });
                } else {
                    label.setText("");
                }
            }
        });


        get("slot1").add(button);
        get("slot2").add(label);

        getBodyElement().removeChild(get("loading").getElement());
    }
}

