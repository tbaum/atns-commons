package de.atns.common.gwt.client.async;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author tbaum
 * @since 19.11.11
 */
public interface DispatcherStateChangeHandler extends EventHandler {
// -------------------------- OTHER METHODS --------------------------

    void onDispatcherStateChange(DispatcherStateChange event);
}
