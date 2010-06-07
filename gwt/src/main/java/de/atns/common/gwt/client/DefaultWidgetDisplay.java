package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import static com.google.gwt.dom.client.Style.BorderStyle.SOLID;
import static com.google.gwt.dom.client.Style.Position.ABSOLUTE;
import static com.google.gwt.dom.client.Style.Unit.PX;

public abstract class DefaultWidgetDisplay extends Composite implements WidgetDisplay {
// ------------------------------ FIELDS ------------------------------

    private final FlowPanel loader = GwtUtil.flowPanel(new Image("spinner.gif"));

// --------------------------- CONSTRUCTORS ---------------------------

    protected DefaultWidgetDisplay() {
        final Style style = loader.getElement().getStyle();
        style.setPosition(ABSOLUTE);
        style.setTop(30, PX);
        style.setRight(20, PX);
        style.setBackgroundColor("#ffffff");
        style.setBorderWidth(2, PX);
        style.setBorderStyle(SOLID);
        style.setBorderColor("black");
        style.setPadding(4, PX);
        loader.setVisible(false);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public FlowPanel getLoader() {
        return loader;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override public void startProcessing() {
        loader.setVisible(true);
    }

    @Override public void stopProcessing() {
        loader.setVisible(false);
    }

// --------------------- Interface WidgetDisplay ---------------------

    @Override public Widget asWidget() {
        return this;
    }

// -------------------------- OTHER METHODS --------------------------

    public abstract void reset();
}
