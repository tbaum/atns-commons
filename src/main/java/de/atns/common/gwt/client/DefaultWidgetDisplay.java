package de.atns.common.gwt.client;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public abstract class DefaultWidgetDisplay extends Composite implements WidgetDisplay {

    private final FlowPanel errorPanel = new FlowPanel();
    private final Label errorLabel = GwtUtil.createLabel("", "errorPanelText");

    protected DefaultWidgetDisplay() {
        errorPanel.addStyleName("textErrorBox");
        errorPanel.add(errorLabel);
        errorPanel.setVisible(false);
    }

    public FlowPanel getErrorPanel() {
        return errorPanel;
    }

    @Override public Widget asWidget() {
        return this;
    }

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
