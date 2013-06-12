package de.atns.common.gwt.client.composite;

import com.google.gwt.user.client.ui.TextBox;

/**
 * @author mwolter
 * @since 29.10.2009 11:20:03
 */
public class LabelTextBox extends LabelBox {
    public LabelTextBox(final String stdText, final int length, final int left) {
        super(new TextBox(), stdText, length, left);
    }
}
