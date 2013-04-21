package de.atns.common.crud.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

/**
 * @author mwolter
 * @since 08.01.2010
 */
public abstract class EnterKeyPressHandler implements KeyPressHandler {

    @Override public void onKeyPress(final KeyPressEvent event) {
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
            onEnterPressed();
        }
    }

    protected abstract void onEnterPressed();
}
