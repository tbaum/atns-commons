package de.atns.common.gwt.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author mwolter
 * @since 22.02.2010 16:18:05
 */
public interface WidgetDisplay {
// -------------------------- OTHER METHODS --------------------------

    Widget asWidget();

    void reset();

    void resetErrors();

    void setErrorVisible(boolean visible);

    void showError(String text);

    void startProcessing();

    void stopProcessing();
}
