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

    private final FlowPanel panel = new FlowPanel();
    private int count = 0;
    private int padding = 0;

    public static ExtendedFlowPanel extendedFlowPanel(final Widget... items) {
        final ExtendedFlowPanel flowPanel = new ExtendedFlowPanel();
        for (final Widget item : items) {
            flowPanel.add(item);
        }
        return flowPanel;
    }

    public ExtendedFlowPanel add(final Widget widget) {
        addNoStyle(widget);
        updateStyle(widget);
        return this;
    }

    public ExtendedFlowPanel addNoStyle(final Widget widget) {
        panel.add(widget);
        count++;
        return this;
    }

    private void updateStyle(final Widget widget) {
        widget.getElement().getStyle().setDisplay(Style.Display.INLINE_BLOCK);
        widget.getElement().getStyle().setPaddingLeft(padding, Style.Unit.PX);
        widget.getElement().getStyle().setPaddingRight(padding, Style.Unit.PX);
    }

    public static ExtendedFlowPanel extendedFlowPanel(final int padding, final Widget... items) {
        final ExtendedFlowPanel flowPanel = extendedFlowPanel();
        flowPanel.setPadding(padding);
        for (final Widget item : items) {
            flowPanel.add(item);
        }
        return flowPanel;
    }

    public ExtendedFlowPanel setPadding(final int padding) {
        this.padding = padding;
        return this;
    }

    private ExtendedFlowPanel() {
    }

    public FlowPanel getPanel() {
        return panel;
    }

    public ExtendedFlowPanel add(final Object text) {
        return add(GwtUtil.createLabel(text != null ? text.toString() : ""));
    }

    public ExtendedFlowPanel addStyle(final String styleName) {
        panel.getWidget(count - 1).addStyleName(styleName);
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
        addNoStyle(widget);
        panel.getWidget(count - 1).getElement().getStyle().setDisplay(BLOCK);
        return this;
    }

    public ExtendedFlowPanel widthPC(final double w) {
        panel.getWidget(count - 1).setWidth(w + "%");
        return this;
    }

    public ExtendedFlowPanel widthPX(final int w) {
        panel.getWidget(count - 1).setWidth(w + "px");
        return this;
    }
}
