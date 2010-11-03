package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasName;
import org.cobogw.gwt.user.client.ui.Button;

import static com.google.gwt.dom.client.Style.Cursor.POINTER;
import static com.google.gwt.dom.client.Style.Overflow.HIDDEN;
import static com.google.gwt.dom.client.Style.Position.ABSOLUTE;
import static com.google.gwt.dom.client.Style.Position.RELATIVE;
import static com.google.gwt.dom.client.Style.Unit.PX;

/**
 * @author mwolter
 * @since 28.04.2010 11:45:37
 */
public class StyledFileUpload extends Composite implements HasChangeHandlers, HasName {
// ------------------------------ FIELDS ------------------------------

    private final FileUpload fileUpload = new FileUpload();
    private Button label;

// --------------------------- CONSTRUCTORS ---------------------------

    public StyledFileUpload(final String text, final int width, final int height) {
        label = new Button(text);
        final FlowPanel flowPanel = GwtUtil.flowPanel(fileUpload, label);

        final Style style1 = label.getElement().getStyle();
        style1.setPosition(ABSOLUTE);
        style1.setCursor(POINTER);
        style1.setZIndex(1);
        style1.setHeight(height, PX);
        style1.setLeft(1, PX);
        style1.setWidth(width, PX);

        final Style style2 = fileUpload.getElement().getStyle();
        style2.setPosition(ABSOLUTE);
        style2.setCursor(POINTER);
        style2.setOpacity(0.01);
        style2.setZIndex(2);
        style2.setHeight(height, PX);
        style2.setWidth(width, PX);

        final Style style3 = flowPanel.getElement().getStyle();
        style3.setPosition(RELATIVE);
        style3.setCursor(POINTER);
        style3.setHeight(height, PX);
        style3.setOverflow(HIDDEN);
        style3.setWidth(width, PX);

        initWidget(flowPanel);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HasChangeHandlers ---------------------

    @Override public HandlerRegistration addChangeHandler(final ChangeHandler handler) {
        return fileUpload.addChangeHandler(handler);
    }

// --------------------- Interface HasName ---------------------

    @Override public void setName(final String name) {
        fileUpload.setName(name);
    }

    @Override public String getName() {
        return fileUpload.getName();
    }

// -------------------------- OTHER METHODS --------------------------

    public String getFilename() {
        return fileUpload.getFilename();
    }

    public void setText(final String text) {
        label.setText(text);
    }
}
