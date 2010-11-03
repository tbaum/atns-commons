package de.atns.common.gwt.client;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ButtonBase;

/**
 * @author mwolter
 * @since 25.05.2010 11:46:07
 */
public class ImageButton extends ButtonBase {
// --------------------------- CONSTRUCTORS ---------------------------

    public ImageButton() {
        super(Document.get().createImageElement());
        getElement().getStyle().setCursor(Style.Cursor.POINTER);
    }

    protected ImageButton(Element elem) {
        super(elem);
        getElement().getStyle().setCursor(Style.Cursor.POINTER);
    }

    public ImageButton(String src) {
        this();
        getElement().setAttribute("src", src);
    }

    public ImageButton(String src, ClickHandler handler) {
        this(src);
        addClickHandler(handler);
    }

    public ImageButton(final String src, final String alt) {
        this(src);
        getElement().setTitle(alt);
    }

// -------------------------- OTHER METHODS --------------------------

    public void click() {
        getButtonElement().click();
    }

    protected ButtonElement getButtonElement() {
        return getElement().cast();
    }

    public void setSrc(String src) {
        getElement().setAttribute("src", src);
    }
}
