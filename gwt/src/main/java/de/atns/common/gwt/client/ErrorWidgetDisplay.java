package de.atns.common.gwt.client;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

/**
 * @author mwolter
 * @since 22.02.2010 16:18:05
 */
public interface ErrorWidgetDisplay extends WidgetDisplay {
// -------------------------- OTHER METHODS --------------------------

    void reset();

    void resetErrors();

    void setErrorVisible(boolean visible);

    void showError(String text);
}