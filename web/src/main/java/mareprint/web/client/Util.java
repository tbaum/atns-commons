package mareprint.web.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Cookies;

import java.util.List;
import java.util.Date;

/**
 * @author tbaum
 * @since 29.06.2009 17:39:08
 */
public class Util {
// -------------------------- STATIC METHODS --------------------------

    public static Widget vertical(final Widget... w) {
        if (w.length == 0) return null;
        if (w.length == 1) return w[0];

        VerticalPanel v = new VerticalPanel();
        for (Widget widget : w) {
            v.add(widget);
        }
        return v;
    }

    public static Widget horizontal(final Widget... w) {
        if (w.length == 0) return null;
        if (w.length == 1) return w[0];

        HorizontalPanel v = new HorizontalPanel();
        for (Widget widget : w) {
            v.add(widget);
        }
        return v;
    }

    static String join(final List<String> err, final String sep1, final String sep2) {
        String fields = "";
        for (int i = 0, errSize = err.size(); i < errSize; i++) {
            final String s = err.get(i);
            if (fields.length() > 0) fields += (i < errSize - 1 ? sep1 : sep2);
            fields += s;
        }
        return fields;
    }

    public static Label head1(String s) {
        Label label = new Label(s);
        label.addStyleName("head1");
        return label;
    }

    public static Label head2(String s) {
        Label label = new Label(s);
        label.addStyleName("head2");
        return label;
    }

    public static void persist(TextBox t, String id) {
        long i = 86400L * 30 * 1000;
        Cookies.setCookie(id, t.getValue(), new Date(new Date().getTime() + i));
    }

    public static void restore(TextBox t, String id) {
        t.setValue(Cookies.getCookie(id));
    }
}
