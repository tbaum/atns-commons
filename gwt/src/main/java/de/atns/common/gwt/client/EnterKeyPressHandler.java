package de.atns.common.gwt.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

/**
 * @author mwolter
 * @since 08.01.2010
 */
abstract class EnterKeyPressHandler implements KeyPressHandler {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface KeyPressHandler ---------------------

    @Override public void onKeyPress(final KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
            onEnterPressed();
        }
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract void onEnterPressed();
}