package de.atns.common.gwt.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author tbaum
 * @since 20.10.10
 */
public class TableRow extends FlowPanel {
    TableRow() {
        setStyleName("row");
    }

    TableRow(String head) {
        setStyleName(head);
    }

    public void addCells(Object... w) {
        for (int i = 0, w1Length = w.length; i < w1Length; i++) {
            Object widget = w[i];
            final Widget widget1;
            if (widget instanceof Widget) {
                widget1 = (Widget) widget;
            } else {
                widget1 = new HTML(widget != null ? widget.toString() : "");
            }
            widget1.addStyleName("cell");
            widget1.addStyleName("cell" + i);
            add(widget1);
        }
    }
}
