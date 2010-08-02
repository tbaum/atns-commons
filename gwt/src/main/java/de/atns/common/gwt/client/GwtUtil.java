package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
import org.cobogw.gwt.user.client.ui.Button;

import static com.google.gwt.dom.client.Style.Cursor.POINTER;
import static com.google.gwt.dom.client.Style.Display.INLINE;
import static com.google.gwt.dom.client.Style.TextDecoration.UNDERLINE;

/**
 * @author mwolter
 * @since 19.11.2009 20:03:32
 */
@SuppressWarnings({"ALL"}) public class GwtUtil {
// ------------------------------ FIELDS ------------------------------

    public static final Command NOOP = new Command() {
        @Override public void execute() {
        }
    };

// -------------------------- STATIC METHODS --------------------------

    public static FlowPanel flowPanel(Widget... w) {
        FlowPanel fp = new FlowPanel();
        for (Widget widget : w) {
            fp.add(widget);
        }
        return fp;
    }

    public static FlowPanel flowPanel(String style, Widget... w) {
        FlowPanel fp = flowPanel(w);
        fp.setStyleName(style);
        return fp;
    }

    public static Widget divBoxBorder(final Widget inhalt, final String width, DivBoxColor color) {
        FlowPanel ro = new FlowPanel();

        String erweiterung = color.name().toLowerCase();

        ro.addStyleName("divBox-ro_" + erweiterung);
        ro.setWidth(width);

        FlowPanel lo = new FlowPanel();
        lo.addStyleName("divBox-lo_" + erweiterung);

        FlowPanel lu = new FlowPanel();
        lu.addStyleName("divBox-lu_" + erweiterung);

        FlowPanel ru = new FlowPanel();
        ru.addStyleName("divBox-ru_" + erweiterung);

        ro.add(lo);
        lo.add(ru);
        ru.add(lu);
        lu.add(inhalt);

        return ro;
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
        Button button = new Button(text, clickHandler);
        button.setStyleName(style);
        return button;
    }

    public static Label imageAnchor(final Image image, final String historyLink) {
        return anchor(image.getElement().getInnerHTML(), historyLink, null);
    }

    public static Label anchor(final String text, final String historyLink, final String style) {
        Label label = createLabel(text, style, true);

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

    public static FlowPanel createFieldSet(String legend, Widget content) {
        Element fieldset = DOM.createFieldSet();
        fieldset.setInnerHTML("<legend>" + legend + "</legend>");
        DOM.appendChild(fieldset, content.getElement());
        fieldset.getStyle().setMargin(0, Style.Unit.PX);

        FlowPanel flowPanel = new FlowPanel();
        DOM.appendChild(flowPanel.getElement(), fieldset);

        return flowPanel;
    }

// -------------------------- ENUMERATIONS --------------------------

    public enum DivBoxColor {
        WEISS, BLAU, ROT
    }
}
