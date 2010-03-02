package de.atns.common.gwt;

import net.customware.gwt.presenter.client.Display;

/**
 * @author mwolter
 * @since 22.02.2010 16:18:05
 */
public interface ErrorDisplay extends Display {
// -------------------------- OTHER METHODS --------------------------

    void showError(Throwable originalCaught);

    void hideError();
}
