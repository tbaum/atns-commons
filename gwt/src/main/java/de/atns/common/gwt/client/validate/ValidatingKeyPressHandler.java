package de.atns.common.gwt.client.validate;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.TextBox;
import de.atns.common.gwt.client.composite.LabelTextBox;


/**
 * @author mwolter
 * @since 02.08.2010 19:40:32
 */
public abstract class ValidatingKeyPressHandler implements KeyPressHandler {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface KeyPressHandler ---------------------

    @Override public void onKeyPress(final KeyPressEvent event) {
        // erlaube cursor, control, meta-tasten ....
        if (event.isAltKeyDown() || event.isControlKeyDown() || event.isMetaKeyDown() ||
                event.getNativeEvent().getKeyCode() != 0) {
            return;
        }

        if (!isValid(event)) {
            Object source = event.getSource();
            if (source instanceof TextBox) {
                ((TextBox) source).cancelKey();
            } else if (source instanceof LabelTextBox) {
                ((LabelTextBox) source).cancelKey();
            } else {
                throw new RuntimeException("invalid implementation for " + source.getClass());
            }
        }
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract boolean isValid(final KeyPressEvent event);
}
