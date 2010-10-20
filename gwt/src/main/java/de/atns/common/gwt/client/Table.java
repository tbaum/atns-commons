package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author tbaum
 * @since 20.10.10
 */
public class Table extends ComplexPanel {
    public static Table table(Row... w) {
        Table fp = new Table();
        fp.add(w);
        return fp;
    }

    public void add(Row... w) {
        for (Row row : w) {
            add(row);
        }
    }

    public static Table table(String style, Row... w) {
        Table fp = table(w);
        fp.addStyleName(style);
        return fp;
    }

    public static Row head(Object... w) {
        Row fp = new Row();
        fp.setStyleName("header");
        fp.add(w);
        return fp;
    }

    public static Row row(Object... w) {
        Row fp = new Row();
        fp.setStyleName("row");
        fp.add(w);
        return fp;
    }

    Table() {
        setElement(DOM.createElement("table"));
        getElement().setAttribute("cellspacing", "0");
    }

    @Override
    public void add(Widget child) {
        add(child, getElement());
    }

    private static class Cell extends SimplePanel {
        Cell(IsWidget w) {
            super(DOM.createElement("td"));
            setWidget(w);
        }

        Cell(String w) {
            super(DOM.createElement("td"));
            getElement().setInnerHTML(w);
        }
    }

    public static class Row extends ComplexPanel {
        @Override
        public void add(Widget w) {
            add(w, getElement());
        }

        public void add(Object... w) {
            for (int i = 0, w1Length = w.length; i < w1Length; i++) {
                Object widget = w[i];
                final Cell tc;

                if (widget instanceof Cell) {
                    tc = (Cell) widget;
                } else if (widget instanceof Widget) {
                    tc = new Cell((IsWidget) widget);
                } else {
                    tc = new Cell(widget != null ? widget.toString() : "");
                }


                tc.addStyleName("cell");
                tc.addStyleName("cell" + i);

                add(tc);
            }
        }

        private Row() {
            setElement(DOM.createElement("tr"));
        }
    }
}
