package de.atns.common.gwt.client.validate;

import com.google.gwt.event.dom.client.KeyPressEvent;

public class ValidatingPhoneDigitsKeyPressHandler extends ValidatingKeyPressHandler {

    @Override protected boolean isValid(final KeyPressEvent event) {
        return Character.isDigit(event.getCharCode()) || event.getCharCode() == '+';
    }
}
