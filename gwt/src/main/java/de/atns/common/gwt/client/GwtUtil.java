package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

import static com.google.gwt.dom.client.Style.Cursor.POINTER;
import static com.google.gwt.dom.client.Style.Display.*;
import static com.google.gwt.dom.client.Style.TextDecoration.UNDERLINE;

/**
 * @author mwolter
 * @since 19.11.2009 20:03:32
 */
public class GwtUtil {

    public static final Command NOOP = new Command() {

        @Override public void execute() {
        }
    };

    public static FlowPanel flowPanel(final IsWidget... w) {
        final FlowPanel fp = new FlowPanel();
        for (final IsWidget widget : w) {
            fp.add(widget);
        }
        return fp;
    }

    public static FlowPanel flowPanel(final String style, final IsWidget... w) {
        final FlowPanel fp = flowPanel(w);
        fp.setStyleName(style);
        return fp;
    }

    public static Widget divBoxBorder(final Widget inhalt, final String width, final DivBoxColor color) {
        final FlowPanel ro = new FlowPanel();

        final String erweiterung = color.name().toLowerCase();

        ro.addStyleName("divBox-ro_" + erweiterung);
        ro.setWidth(width);

        final FlowPanel lo = new FlowPanel();
        lo.addStyleName("divBox-lo_" + erweiterung);

        final FlowPanel lu = new FlowPanel();
        lu.addStyleName("divBox-lu_" + erweiterung);

        final FlowPanel ru = new FlowPanel();
        ru.addStyleName("divBox-ru_" + erweiterung);

        ro.add(lo);
        lo.add(ru);
        ru.add(lu);
        lu.add(inhalt);

        return ro;
    }

    public static void openWindow(String url, String window, int i, int i1) {
        Window.open(url, window, "location=yes, status=yes, scrollbars=yes, width=" + i + ", height=" + i1);
    }

    public static boolean checkTelefonSymbol(final char code) {
        return !(Character.isDigit(code) || code == '+');
    }

    static CheckBox checkBox(final String label, final String style) {
        final CheckBox checkBox = new CheckBox(label, true);
        checkBox.addStyleName(style);
        return checkBox;
    }

    static Button button(final String text, final String style, final ClickHandler clickHandler) {
        final Button button = new Button(text, clickHandler);
        button.setStyleName(style);
        return button;
    }

    public static Label imageAnchor(final Image image, final String historyLink) {
        return anchor(image.getElement().getInnerHTML(), historyLink, null);
    }

    public static Label anchor(final String text, final String historyLink, final String style) {
        final Label label = createLabel(text, style, true);

        final Style labelStyle = label.getElement().getStyle();
        labelStyle.setCursor(POINTER);
        labelStyle.setTextDecoration(UNDERLINE);

        label.addClickHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent clickEvent) {
                History.newItem(historyLink);
            }
        });
        return label;
    }

    public static Label createLabel(final String text, final String style, final boolean inline) {
        final Label label = new Label();
        label.getElement().setInnerHTML(text);
        if (style != null) {
            label.addStyleName(style);
        }

        if (inline) {
            label.getElement().getStyle().setDisplay(INLINE);
        }
        return label;
    }

    public static Label anchor(final String text, final String historyLink) {
        return anchor(text, historyLink, null);
    }

    public static Label createLabel(final String text) {
        return createLabel(text, null, false);
    }

    public static Label createLabel(final String text, final boolean inline) {
        return createLabel(text, null, inline);
    }

    public static Label createLabel(final String text, final String style) {
        return createLabel(text, style, false);
    }

    public static Widget image(final String url, final String style) {
        final Image image = new Image(url);
        image.addStyleName(style);
        return image;
    }

    public static FlowPanel createFieldSet(final String legend, final Widget content) {
        final Element fieldset = DOM.createFieldSet();
        fieldset.setInnerHTML("<legend>" + legend + "</legend>");
        DOM.appendChild(fieldset, content.getElement());
        fieldset.getStyle().setMargin(0, Style.Unit.PX);

        final FlowPanel flowPanel = new FlowPanel();
        DOM.appendChild(flowPanel.getElement(), fieldset);

        return flowPanel;
    }

    public static Image bgImage(ImageResource backgroundImage) {
        Image image = new Image(backgroundImage);
        image.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        return image;
    }

    public static native void closeWindow() /*-{
        $wnd.close();
    }-*/;

    public static Image createImage(ImageResource resource, String alt) {
        return createImage(resource, alt, "");
    }

    public static Image createImage(ImageResource resource, String alt, String style) {
        Image image = new Image(resource);
        image.setTitle(alt);
        if (style != null && !style.isEmpty()) {
            image.addStyleName(style);
        } else {
            image.getElement().getStyle().setProperty("margin", "5px 0 0 11px");
        }
        image.getElement().getStyle().setCursor(POINTER);
        return image;
    }

    public static void setEnabledWidget(boolean editable, HasEnabled focusWidget) {
        focusWidget.setEnabled(editable);
        toogleStyle((Widget) focusWidget, !editable, "gwt-TextBox-readonly");
    }

    public static void toogleStyle(final Widget widget, final boolean b, String style) {
        if (b) {
            widget.addStyleName(style);
        } else {
            widget.removeStyleName(style);
        }
    }

    public static void setEnabledDateBox(boolean editable, DateBox dateBox) {
        dateBox.setEnabled(editable);
        toogleStyle(dateBox, !editable, "gwt-TextBox-readonly");
    }

    public static void setVisibleStyle(final Widget widget, final boolean editable) {
        if (editable) {
            widget.getElement().getStyle().setDisplay(INLINE_BLOCK);
        } else {
            widget.getElement().getStyle().setDisplay(NONE);
        }
    }

    public enum DivBoxColor {

        WEISS, BLAU, ROT
    }
}
