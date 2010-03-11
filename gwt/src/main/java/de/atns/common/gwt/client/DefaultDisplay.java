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

    @Override public void showError(final String text) {
        errorPanel.setVisible(true);
        errorLabel.setText(text);
    }

    @Override public void setErrorVisible(final boolean visible) {
        errorPanel.setVisible(visible);
    }

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

    public void startProcessing() {
        loader.setVisible(true);
    }

    public void stopProcessing() {
        loader.setVisible(false);
    }

// --------------------- Interface WidgetDisplay ---------------------

    public Widget asWidget() {
        return this;
    }

// -------------------------- OTHER METHODS --------------------------

    public void setVisibleError() {
        errorPanel.setVisible(false);
    }

    public void showError(final Throwable originalCaught) {
        errorPanel.setVisible(true);
        errorLabel.setText("Fehler! " + originalCaught.getMessage());
    }
}
