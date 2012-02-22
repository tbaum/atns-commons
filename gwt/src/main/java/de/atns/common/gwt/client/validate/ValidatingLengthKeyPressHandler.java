package de.atns.common.gwt.client.validate;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.ui.TextBox;

public class ValidatingLengthKeyPressHandler extends ValidatingKeyPressHandler {

    private final int maxLength;

    public ValidatingLengthKeyPressHandler(final int maxLength) {
        this.maxLength = maxLength;
    }

    @Override protected boolean isValid(final KeyPressEvent event) {
        final TextBox textBox = (TextBox) event.getSource();
        return textBox.getText().length() < maxLength || textBox.getSelectedText().length() != 0;
    }
}
