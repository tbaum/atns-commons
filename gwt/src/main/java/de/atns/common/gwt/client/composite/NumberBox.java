package de.atns.common.gwt.client.composite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.*;

import java.math.BigDecimal;

/**
 * @author tbaum
 * @since 08.09.2010
 */
public class NumberBox extends Composite
        implements HasValue<Number>, ValueChangeHandler<Number>, Focusable, HasEnabled {
// ------------------------------ FIELDS ------------------------------

    public static final String DEFAULT_STYLENAME = "gwt-NumberBox";

    private static final String NUMBER_BOX_FORMAT_ERROR = "numberBoxFormatError";
    private static final NumberBox.DefaultFormat DEFAULT_FORMAT = GWT.create(NumberBox.DefaultFormat.class);
    private Number numberValue = null;
    private final TextBox box = new TextBox();
    private NumberBox.Format format;

// --------------------------- CONSTRUCTORS ---------------------------

    public NumberBox() {
        this(null, DEFAULT_FORMAT);
    }

    public NumberBox(final Number number, final NumberBox.Format format) {
        assert format != null : "You may not construct a number box with a null format";
        this.format = format;


        initWidget(box);
        setStyleName(DEFAULT_STYLENAME);

        box.addBlurHandler(new BlurHandler() {
            @Override public void onBlur(final BlurEvent blurEvent) {
                final Number parsedNumber = parseNumber(true);
                if (parsedNumber != null) {
                    setValue(numberValue, parsedNumber, true);
                }
            }
        });

        box.addKeyDownHandler(new KeyDownHandler() {
            @Override public void onKeyDown(final KeyDownEvent event) {
                switch (event.getNativeKeyCode()) {
                    case KeyCodes.KEY_ENTER:
                    case KeyCodes.KEY_TAB:
                        final Number parsedNumber = parseNumber(true);
                        if (parsedNumber != null) {
                            setValue(numberValue, parsedNumber, true);
                        }
                        break;
                }
            }
        });

        setValue(number);
    }

    private Number parseNumber(final boolean reportError) {
        if (reportError) {
            format.reset(this, false);
        }
        final String text = box.getText().trim();
        return format.parse(this, text, reportError);
    }

    private void setValue(final Number oldNumber, final Number number, final boolean fireEvents) {
        numberValue = number;
        format.reset(this, false);
        box.setValue(format.format(this, number));

        if (fireEvents) {
            NumberChangeEvent.fireIfNotEqualNumbers(this, oldNumber, number);
        }
    }

    @Override public void setValue(final Number number) {
        setValue(number, false);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Focusable ---------------------

    @Override public int getTabIndex() {
        return box.getTabIndex();
    }

    @Override public void setAccessKey(final char key) {
        box.setAccessKey(key);
    }

    @Override public void setFocus(final boolean focused) {
        box.setFocus(focused);
    }

    @Override public void setTabIndex(final int index) {
        box.setTabIndex(index);
    }

// --------------------- Interface HasEnabled ---------------------

//    public int getCursorPos() {
//        return box.getCursorPos();
//    }
//
//    public TextBox getTextBox() {
//        return box;
//    }

    @Override public boolean isEnabled() {
        return box.isEnabled();
    }

    @Override public void setEnabled(final boolean enabled) {
        box.setEnabled(enabled);
    }

// --------------------- Interface HasValue ---------------------

    @Override public void setValue(final Number number, final boolean fireEvents) {
        setValue(numberValue, number, fireEvents);
    }

// --------------------- Interface HasValueChangeHandlers ---------------------

    @Override public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<Number> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

// --------------------- Interface ValueChangeHandler ---------------------

    @Override public void onValueChange(final ValueChangeEvent<Number> event) {
        setValue(parseNumber(false), event.getValue(), true);
        box.setFocus(true);
    }

// -------------------------- OTHER METHODS --------------------------

    public HandlerRegistration addBlurHandler(BlurHandler handler) {
        return box.addBlurHandler(handler);
    }

    public HandlerRegistration addFocusHandler(FocusHandler handler) {
        return box.addFocusHandler(handler);
    }

    public HandlerRegistration addKeyDownHandler(final KeyDownHandler handler) {
        return box.addKeyDownHandler(handler);
    }

    public HandlerRegistration addKeyPressHandler(final KeyPressHandler handler) {
        return box.addKeyPressHandler(handler);
    }

    public HandlerRegistration addKeyUpHandler(final KeyUpHandler handler) {
        return box.addKeyUpHandler(handler);
    }

    public BigDecimal toBigDecimal() {
        return toBigDecimal(BigDecimal.ZERO);
    }

    public BigDecimal toBigDecimal(BigDecimal defaultValue) {
        Number value = getValue();
        return value != null ? new BigDecimal(value.doubleValue()) : defaultValue;
    }

    @Override public Number getValue() {
        return parseNumber(true);
    }

// -------------------------- INNER CLASSES --------------------------

    public static class DefaultFormat implements NumberBox.Format {
        private final NumberFormat numberFormat;

        public DefaultFormat() {
            numberFormat = NumberFormat.getDecimalFormat();
        }

        public DefaultFormat(final NumberFormat numberFormat) {
            this.numberFormat = numberFormat;
        }

        @Override public String format(final NumberBox numberBox, final Number number) {
            if (number == null) {
                return "";
            } else {
                return numberFormat.format(number);
            }
        }

        public NumberFormat getNumberFormat() {
            return numberFormat;
        }

        @Override @SuppressWarnings("deprecation")
        public Number parse(final NumberBox numberBox, final String numberText, final boolean reportError) {
            Number number = null;
            try {
                if (numberText.length() > 0) {
                    number = numberFormat.parse(numberText);
                }
            } catch (IllegalArgumentException exception) {
                try {
                    number = new BigDecimal(numberText);
                } catch (IllegalArgumentException e) {
                    if (reportError) {
                        numberBox.addStyleName(NUMBER_BOX_FORMAT_ERROR);
                    }
                    return null;
                }
            }
            return number;
        }

        @Override public void reset(final NumberBox numberBox, final boolean abandon) {
            numberBox.removeStyleName(NUMBER_BOX_FORMAT_ERROR);
        }
    }

    public interface Format {
        String format(NumberBox numberBox, Number number);

        Number parse(NumberBox numberBox, String text, boolean reportError);

        void reset(NumberBox numberBox, boolean abandon);
    }
}

