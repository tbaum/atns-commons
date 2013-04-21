package de.atns.common.gwt.client.validate;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;

public class ValidatingLengthKeyUpHandler implements KeyUpHandler {

    private final int maxLength;

    public ValidatingLengthKeyUpHandler(final int maxLength) {
        this.maxLength = maxLength;
    }

    @Override public void onKeyUp(KeyUpEvent event) {
        final TextBox textBox = (TextBox) event.getSource();
        String text = textBox.getText();
        if (text.length() >= maxLength) {
            textBox.setText(text.substring(0, maxLength));
        }
    }
}
