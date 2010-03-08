package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
import org.cobogw.gwt.user.client.ui.Button;

import static com.google.gwt.dom.client.Style.Cursor.POINTER;
import static com.google.gwt.dom.client.Style.Display.INLINE;
import static com.google.gwt.dom.client.Style.TextDecoration.UNDERLINE;
import static com.google.gwt.event.dom.client.KeyCodes.*;

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

    public static boolean checkNeutraleTasten(final KeyPressEvent event) {
        final char code = event.getCharCode();
        return code != KEY_BACKSPACE && code != KEY_ENTER && code != KEY_TAB && code != KEY_DELETE;
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

    public static Button createDeleteButton(final String text, final ClickHandler deleteHandler) {
        final Button deleteButton = new Button("Löschen");

        deleteButton.addClickHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                final DialogBox dialogBox = new DialogBox(false, true);
                dialogBox.setText("Löschen bestätigen");

                FlowPanel flowPanel = new FlowPanel();

                final Label w = new Label(text, true);
                w.addStyleName("heading");
                w.getElement().getStyle().setPadding(15, Style.Unit.PX);
                flowPanel.add(w);


                Button cancelButton = new Button("Abbrechen");
                cancelButton.getElement().getStyle().setPaddingLeft(10, Style.Unit.PX);

                final ClickHandler clickHandler = new ClickHandler() {
                    @Override public void onClick(final ClickEvent event) {
                        dialogBox.hide();
                    }
                };


                Button delButton = new Button("Löschen");
                delButton.addClickHandler(deleteHandler);
                delButton.addClickHandler(clickHandler);
                cancelButton.addClickHandler(clickHandler);

                flowPanel.add(delButton);
                flowPanel.add(cancelButton);

                dialogBox.add(flowPanel);
                flowPanel.setWidth("500px");

                flowPanel.getElement().getStyle().setProperty("textAlign", "center");
                flowPanel.getElement().getStyle().setProperty("paddingBottom", "10px");

                dialogBox.setGlassEnabled(true);
                dialogBox.center();
                dialogBox.show();
            }
        });

        return deleteButton;
    }

// -------------------------- ENUMERATIONS --------------------------

    public enum DivBoxColor {
        WEISS, BLAU
    }
}
