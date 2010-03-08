package de.atns.common.gwt.client;

import net.customware.gwt.presenter.client.Display;

/**
 * @author mwolter
 * @since 22.02.2010 16:18:05
 */
public interface ErrorDisplay extends Display {
// -------------------------- OTHER METHODS --------------------------

    void setErrorVisible(boolean visible);
    void showError(String text);
}
