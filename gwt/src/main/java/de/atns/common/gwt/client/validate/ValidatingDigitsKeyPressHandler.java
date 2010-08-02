package de.atns.common.gwt.client.validate;

import com.google.gwt.event.dom.client.KeyPressEvent;

public class ValidatingDigitsKeyPressHandler extends ValidatingKeyPressHandler {
// -------------------------- OTHER METHODS --------------------------

    @Override protected boolean isValid(final KeyPressEvent event) {
        return Character.isDigit(event.getCharCode());
    }
}
