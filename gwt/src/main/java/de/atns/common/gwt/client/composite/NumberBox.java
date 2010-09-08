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
public class NumberBox extends Composite implements HasValue<Number>, ValueChangeHandler<Number>, Focusable, HasEnabled {
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

    public NumberBox(Number number, NumberBox.Format format) {
        assert format != null : "You may not construct a number box with a null format";
        this.format = format;


        initWidget(box);
        setStyleName(DEFAULT_STYLENAME);

        box.addBlurHandler(new BlurHandler() {
            @Override public void onBlur(final BlurEvent blurEvent) {
                Number parsedNumber = parseNumber(true);
                if (parsedNumber != null) {
                    setValue(numberValue, parsedNumber, true);
                }
            }
        });

        box.addKeyDownHandler(new KeyDownHandler() {
            public void onKeyDown(KeyDownEvent event) {
                switch (event.getNativeKeyCode()) {
                    case KeyCodes.KEY_ENTER:
                    case KeyCodes.KEY_TAB:
                        Number parsedNumber = parseNumber(true);
                        if (parsedNumber != null) {
                            setValue(numberValue, parsedNumber, true);
                        }
                        break;
                }
            }
        });

        setValue(number);
    }

    private Number parseNumber(boolean reportError) {
        if (reportError) {
            format.reset(this, false);
        }
        String text = box.getText().trim();
        return format.parse(this, text, reportError);
    }

    private void setValue(Number oldNumber, Number number, boolean fireEvents) {
        numberValue = number;
        format.reset(this, false);
        box.setValue(format.format(this, number));

        if (fireEvents) {
            NumberChangeEvent.fireIfNotEqualNumbers(this, oldNumber, number);
        }
    }

    public void setValue(Number number) {
        setValue(number, false);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Focusable ---------------------

    public int getTabIndex() {
        return box.getTabIndex();
    }

    public void setAccessKey(char key) {
        box.setAccessKey(key);
    }

    public void setFocus(boolean focused) {
        box.setFocus(focused);
    }

    public void setTabIndex(int index) {
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

    public void setEnabled(boolean enabled) {
        box.setEnabled(enabled);
    }

// --------------------- Interface HasValue ---------------------

    public void setValue(Number number, boolean fireEvents) {
        setValue(numberValue, number, fireEvents);
    }

// --------------------- Interface HasValueChangeHandlers ---------------------

    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Number> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

// --------------------- Interface TakesValue ---------------------

    public Number getValue() {
        return parseNumber(true);
    }

// --------------------- Interface ValueChangeHandler ---------------------

    public void onValueChange(ValueChangeEvent<Number> event) {
        setValue(parseNumber(false), event.getValue(), true);
        box.setFocus(true);
    }

// -------------------------- INNER CLASSES --------------------------

    public static class DefaultFormat implements NumberBox.Format {
        private final NumberFormat numberFormat;

        public DefaultFormat() {
            numberFormat = NumberFormat.getDecimalFormat();
        }

        public DefaultFormat(NumberFormat numberFormat) {
            this.numberFormat = numberFormat;
        }

        public String format(NumberBox numberBox, Number number) {
            if (number == null) {
                return "";
            } else {
                return numberFormat.format(number);
            }
        }

        public NumberFormat getNumberFormat() {
            return numberFormat;
        }

        @SuppressWarnings("deprecation")
        public Number parse(NumberBox numberBox, String numberText, boolean reportError) {
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

        public void reset(NumberBox numberBox, boolean abandon) {
            numberBox.removeStyleName(NUMBER_BOX_FORMAT_ERROR);
        }
    }

    public interface Format {
        String format(NumberBox numberBox, Number number);

        Number parse(NumberBox numberBox, String text, boolean reportError);

        void reset(NumberBox numberBox, boolean abandon);
    }
}

