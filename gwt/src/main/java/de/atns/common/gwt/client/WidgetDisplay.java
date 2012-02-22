package de.atns.common.gwt.client;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author mwolter
 * @since 22.02.2010 16:18:05
 */
public interface WidgetDisplay extends IsWidget {

    void reset();

    void resetErrors();

    void setErrorVisible(boolean visible);

    void showError(String text);

    void startProcessing();

    void stopProcessing();
}
