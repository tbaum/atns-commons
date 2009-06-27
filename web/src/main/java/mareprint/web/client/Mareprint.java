package mareprint.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.DivElement;
import static com.google.gwt.user.client.DOM.createDiv;
import com.google.gwt.user.client.ui.HTML;
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
        final HTML debug = new HTML();
        debug.addStyleName("debugPanel");

        DivElement placeHolder = createDiv().cast();
        placeHolder.setId("swfupload");
        getBodyElement().appendChild(placeHolder);


        final UploadFilesComponent uploadFiles = new UploadFilesComponent(placeHolder) {
            @Override protected void debug(final String s) {
                debug.setHTML(s);
            }
        };

        get().add(uploadFiles);
        get().add(debug);

        getBodyElement().removeChild(get("loading").getElement());
    }
}