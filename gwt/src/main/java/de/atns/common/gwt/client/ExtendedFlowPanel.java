package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.dom.client.Style.Display.BLOCK;

/**
 * @author tbaum
 * @since 12.02.2010
 */
public class ExtendedFlowPanel {
// ------------------------------ FIELDS ------------------------------

    private final FlowPanel panel = new FlowPanel();

    private int count = 0;
    private int padding = 0;

// -------------------------- STATIC METHODS --------------------------

    public static ExtendedFlowPanel extendedFlowPanel() {
        return new ExtendedFlowPanel();
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private ExtendedFlowPanel() {
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public FlowPanel getPanel() {
        return panel;
    }

// -------------------------- OTHER METHODS --------------------------

    public ExtendedFlowPanel add(Widget widget) {
        panel.add(widget);
        count++;
        panel.getWidget(count - 1).getElement().getStyle().setDisplay(Style.Display.INLINE);
        return this;
    }

    public ExtendedFlowPanel addStyle(String styleName) {
        panel.getWidget(count - 1).addStyleName(styleName);
        return this;
    }

    public ExtendedFlowPanel add(Object text) {
        panel.add(GwtUtil.createLabel(text != null ? text.toString() : ""));
        count++;
        return this;
    }

    public ExtendedFlowPanel clear() {
        panel.clear();
        count = 0;
        return this;
    }

    public ExtendedFlowPanel newLine() {
        final HTML widget = new HTML("");
        widget.getElement().getStyle().setPadding(padding, Style.Unit.PX);
        panel.add(widget);
        count++;
        panel.getWidget(count - 1).getElement().getStyle().setDisplay(BLOCK);
        return this;
    }

    public ExtendedFlowPanel widthPX(int w) {
        panel.getWidget(count - 1).setWidth(w + "px");
        return this;
    }

    public ExtendedFlowPanel widthPC(double w) {
        panel.getWidget(count - 1).setWidth(w + "%");
        return this;
    }

    public ExtendedFlowPanel setPadding(final int padding) {
        this.padding = padding;
        return this;
    }
}