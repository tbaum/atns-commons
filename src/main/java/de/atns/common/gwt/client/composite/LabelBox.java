package de.atns.common.gwt.client.composite;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;

import static com.google.gwt.dom.client.Style.Unit.PX;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;

/**
 * @author mwolter
 * @since 29.10.2009 11:20:03
 */
public class LabelBox extends Composite implements HasValue<String>, FocusHandler, BlurHandler, KeyUpHandler {

    private final TextBox textBox;

    private final InlineLabel infoLabel = new InlineLabel();

    public LabelBox(TextBox box, final String stdText, final int length, final int left) {
        textBox = box;
        textBox.setVisibleLength(length);

        textBox.getElement().getStyle().setWidth(length, PX);
        textBox.getElement().getStyle().setLeft(left, PX);

        textBox.addFocusHandler(this);
        textBox.addBlurHandler(this);
        textBox.addKeyUpHandler(this);
        infoLabel.addStyleName("label");

        infoLabel.setText(stdText);
        infoLabel.addClickHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent clickEvent) {
                textBox.setFocus(true);
            }
        });

        infoLabel.getElement().getStyle().setTop(3, PX);
        infoLabel.getElement().getStyle().setLeft(6 + left, PX);
        initWidget(flowPanel("defValueTextBox", textBox, infoLabel));
    }

    @Override public void onBlur(final BlurEvent blurEvent) {
        updateLabelState();
    }

    @Override public void onFocus(final FocusEvent focusEvent) {
        updateLabelState();
    }

    @Override public void setValue(final String value, final boolean fireEvents) {
        textBox.setValue(value, fireEvents);
        updateLabelState();
    }

    @Override
    public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<String> stringValueChangeHandler) {
        return textBox.addValueChangeHandler(stringValueChangeHandler);
    }

    @Override public void onKeyUp(final KeyUpEvent keyUpEvent) {
        updateLabelState();
    }

    @Override public String getValue() {
        return textBox.getValue();
    }

    @Override public void setValue(final String text) {
        textBox.setValue(text);
        updateLabelState();
    }

    public void addKeyPressHandler(final KeyPressHandler keyPressHandler) {
        textBox.addKeyPressHandler(keyPressHandler);
    }

    public void addKeyUpHandler(final KeyUpHandler keyUpHandler) {
        textBox.addKeyUpHandler(keyUpHandler);
    }

    public void cancelKey() {
        textBox.cancelKey();
    }

    public String getSelectedText() {
        return textBox.getSelectedText();
    }

    public int getTabIndex() {
        return textBox.getTabIndex();
    }

    public String getText() {
        return textBox.getText();
    }

    public void setFocus(final boolean focus) {
        textBox.setFocus(focus);
    }

    public void setLabelText(String title) {
        infoLabel.setText(title);
    }

    private void updateLabelState() {
        //textBox.selectAll();
        final boolean ns = !textBox.getValue().isEmpty();
        infoLabel.setVisible(!ns);
    }
}
