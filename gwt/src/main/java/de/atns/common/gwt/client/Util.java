package de.atns.common.gwt.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author tbaum
 * @since 13.06.2010
 */
public class Util {
// -------------------------- STATIC METHODS --------------------------

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

    public static <E extends Enum> E parseEnum(final ListBox listBox, Class<E> enumClass) {
        final int index = listBox.getSelectedIndex();
        if (index > listBox.getItemCount() || index < 0) return null;
        final String s = listBox.getValue(index);
        return "".equals(s) ? null : (E) (Enum.valueOf(enumClass, s));
    }

    public static void fillListBox(final ListBox listBox, final Map<Long, String> entries, final Long selected) {
        listBox.clear();
        for (Map.Entry<Long, String> entry : entries.entrySet()) {
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
}
