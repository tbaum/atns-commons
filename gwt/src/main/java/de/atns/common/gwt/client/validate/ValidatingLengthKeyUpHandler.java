package de.atns.common.gwt.client.validate;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;

public class ValidatingLengthKeyUpHandler implements KeyUpHandler {
// ------------------------------ FIELDS ------------------------------

    private final int maxLength;

// --------------------------- CONSTRUCTORS ---------------------------

    public ValidatingLengthKeyUpHandler(final int maxLength) {
        this.maxLength = maxLength;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface KeyUpHandler ---------------------

    @Override public void onKeyUp(KeyUpEvent event) {
        final TextBox textBox = (TextBox) event.getSource();
        String text = textBox.getText();
        if (text.length() >= maxLength) {
            textBox.setText(text.substring(0, maxLength));
        }
    }
}


