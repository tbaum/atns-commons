package de.atns.common.gwt.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static com.google.gwt.i18n.client.NumberFormat.getCurrencyFormat;

/**
 * @author tbaum
 * @since 13.06.2010
 */
public class Util {
    public static Date parseDate(final String text, final Date defaultValue) {
        try {
            if (text != null && !text.isEmpty()) {
                return DateTimeFormat.getFormat("dd.MM.yyyy").parse(text);
            }
        } catch (IllegalArgumentException ignored) {
        }
        return defaultValue;
    }

    public static Double parseDouble(final String text, final Double defaultValue) {
        if (text != null && !text.isEmpty()) {
            try {
                return NumberFormat.getFormat("0.00").parse(text);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static Integer parseInt(final String text, final Integer defaultValue) {
        if (text != null && !text.isEmpty()) {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static Long parseLong(final String text, final Long defaultValue) {
        if (text != null && !text.isEmpty()) {
            try {
                return Long.parseLong(text);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    @SuppressWarnings({"unchecked"})
    public static <E extends Enum> E parseEnum(final ListBox listBox, final Class<E> enumClass) {
        final int index = listBox.getSelectedIndex();
        if (index > listBox.getItemCount() || index < 0) {
            return null;
        }
        final String s = listBox.getValue(index);
        return "".equals(s) ? null : (E) (Enum.valueOf(enumClass, s));
    }

    public static void fillListBox(final ListBox listBox, final Map<Long, String> entries, final Long selected) {
        listBox.clear();
        for (final Map.Entry<Long, String> entry : entries.entrySet()) {
            listBox.addItem(entry.getValue(), String.valueOf(entry.getKey()));
            if (entry.getKey().equals(selected)) {
                listBox.setSelectedIndex(listBox.getItemCount() - 1);
            }
        }
    }

    public static String fixValueTrimLc(final TextBox nameTextBox) {
        final String currentValue1 = nameTextBox.getText();
        final String newValue1 = currentValue1.trim().toLowerCase();
        if (!currentValue1.equals(newValue1)) {
            nameTextBox.setValue(newValue1);
        }
        return newValue1;
    }

    public static String fixValueTrim(final TextBox nameTextBox) {
        final String currentValue1 = nameTextBox.getText();
        final String newValue1 = currentValue1.trim();
        if (!currentValue1.equals(newValue1)) {
            nameTextBox.setValue(newValue1);
        }
        return newValue1;
    }

    public static BigDecimal parseBigDecimal(final String text, final BigDecimal defaultValue) {
        return text.isEmpty() ? defaultValue : new BigDecimal(text);
    }

    public static String toString(final Object display) {
        return display.getClass() + "#" + display.hashCode();
    }

    public static DateBox dateBox(String pattern) {
        return new DateBox(new DatePicker(), null, new DateBox.DefaultFormat(DateTimeFormat.getFormat(pattern)));
    }

    public static String parseEur(Number value) {
        return getCurrencyFormat("EUR").format(value);
    }
}
