package de.atns.common.gwt.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public abstract class DefaultErrorWidgetDisplay extends DefaultWidgetDisplay implements ErrorWidgetDisplay {
// ------------------------------ FIELDS ------------------------------

    private final FlowPanel errorPanel = new FlowPanel();
    private final Label errorLabel = GwtUtil.createLabel("", "errorPanelText");

// --------------------------- CONSTRUCTORS ---------------------------

    protected DefaultErrorWidgetDisplay() {
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

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ErrorWidgetDisplay ---------------------

    @Override public void resetErrors() {
        setErrorVisible(false);
    }

    @Override public void setErrorVisible(final boolean visible) {
        errorPanel.setVisible(visible);
    }

    @Override public void showError(final String text) {
        setErrorVisible(true);
        errorLabel.setText(text);
    }
}


