package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;

import static com.google.gwt.dom.client.Style.BorderStyle.SOLID;
import static com.google.gwt.dom.client.Style.Position.ABSOLUTE;
import static com.google.gwt.dom.client.Style.Unit.PX;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public abstract class DefaultDisplay extends Composite implements ErrorWidgetDisplay {
// ------------------------------ FIELDS ------------------------------

    private final FlowPanel loader = GwtUtil.flowPanel(new Image("spinner.gif"));
    private final FlowPanel errorPanel = new FlowPanel();
    private final Label errorLabel = GwtUtil.createLabel("", "errorPanelText");

// --------------------------- CONSTRUCTORS ---------------------------

    protected DefaultDisplay() {
        final Style style = loader.getElement().getStyle();
        style.setPosition(ABSOLUTE);
        style.setTop(30, PX);
        style.setRight(20, PX);
        style.setBackgroundColor("#ffffff");
        style.setBorderWidth(2, PX);
        style.setBorderStyle(SOLID);
        style.setBorderColor("black");
        style.setPadding(4, PX);
        loader.setVisible(false);


        errorPanel.addStyleName("textErrorBox");
        // final Image w = new Image(IMAGES.error());
        // w.getElement().getStyle().setProperty("display", "table-cell");
        // errorPanel.add(w);
        errorPanel.add(errorLabel);
        errorPanel.setVisible(false);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public FlowPanel getErrorPanel() {
        return errorPanel;
    }

    public FlowPanel getLoader() {
        return loader;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override public void startProcessing() {
        loader.setVisible(true);
    }

    @Override public void stopProcessing() {
        loader.setVisible(false);
    }

    //  TODO api change
    // @Override public void reset() {
    // }

// --------------------- Interface ErrorDisplay ---------------------

    @Override public void setErrorVisible(final boolean visible) {
        errorPanel.setVisible(visible);
    }

    @Override public void showError(final String text) {
        errorPanel.setVisible(true);
        errorLabel.setText(text);
    }

// --------------------- Interface WidgetDisplay ---------------------

    @Override public Widget asWidget() {
        return this;
    }
}
