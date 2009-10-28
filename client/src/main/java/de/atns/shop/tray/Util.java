package de.atns.shop.tray;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import org.eclipse.jetty.util.ajax.JSON;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tbaum
 * @since 27.09.2009 13:14:09
 */
public class Util {
// ------------------------------ FIELDS ------------------------------

    public static final FormLayout buttonLayout = new FormLayout("fill:p:g,60dlu,4dlu,60dlu", "");

// -------------------------- STATIC METHODS --------------------------

    public static void center(final JDialog dialog) {
        final Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                dialog.setLocation(screenSize.width / 2 - dialog.getWidth() / 2, screenSize.height / 2 - dialog.getHeight() / 2);
            }
        });
    }

    static void displayException(final Throwable t) {
        final StringBuilder output = new StringBuilder();

        final String msg = t.getMessage();
        if (msg == null || msg.length() == 0) {
            output.append("Fatal: ").append(t.getClass());
        } else {
            output.append(msg);
        }
        output.append("\n\n");
        final StackTraceElement[] st = t.getStackTrace();
        for (int i = 0; i < 10 && i < st.length; i++) {
            output.append(st[i]).append("\n");
        }

        JOptionPane.showMessageDialog(null, output, "General Error", JOptionPane.ERROR_MESSAGE);
    }

    static <T> T[] toArray(final java.util.List<T> request, final Class<T> clazz) {
        @SuppressWarnings({"unchecked"}) final
        T[] ar = (T[]) Array.newInstance(clazz, request.size());

        return request.toArray(ar);
    }

    public static String extractParameter(final Map<String, String[]> params, final String name) {
        final String[] strings = params.get(name);
        if (strings == null || strings.length != 1)
            throw new IllegalArgumentException("Parameter '" + name + "' wrong or missing");
        return strings[0];
    }

    public static JPanel createButtonPanel(final JComponent... components) {
        final DefaultFormBuilder builder = new DefaultFormBuilder(buttonLayout);
        builder.nextColumn();
        for (final JComponent component : components) {
            builder.append(component);
        }
        return builder.getPanel();
    }

    static String mapException(final Throwable e) {
        final Map<String, Serializable> result = new LinkedHashMap<String, Serializable>();
        result.put("failed", true);
        result.put("message", e.getMessage());
        result.put("stacktrace", e.getStackTrace());

        if (e.getCause() != null) {
            result.put("cause", mapException(e.getCause()));
        }

        return JSON.toString(result);
    }
}
