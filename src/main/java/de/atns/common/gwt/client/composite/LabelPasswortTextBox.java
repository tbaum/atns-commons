package de.atns.common.gwt.client.composite;

import com.google.gwt.user.client.ui.PasswordTextBox;

/**
 * @author mwolter
 * @since 29.10.2009 11:20:03
 */
public class LabelPasswortTextBox extends LabelBox {
    public LabelPasswortTextBox(final String stdText, final int length, final int left) {
        super(new PasswordTextBox(), stdText, length, left);
    }
}
