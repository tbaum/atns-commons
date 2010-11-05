package de.atns.common.gwt.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author tbaum
 * @since 20.10.10
 */
public class Table extends ComplexPanel {
// -------------------------- STATIC METHODS --------------------------

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

    public static Cell cell(IsWidget w) {
        return new Cell(w);
    }

    public static Cell cell(String s) {
        return new Cell(s);
    }

    public static Cell cell(String style, IsWidget w) {
        return new Cell(style, w);
    }

    public static Cell cell(String style, String s) {
        return new Cell(style, s);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    Table() {
        setElement(DOM.createElement("table"));
        getElement().setAttribute("cellspacing", "0");
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HasWidgets ---------------------

    @Override
    public void add(Widget child) {
        add(child, getElement());
    }

// -------------------------- INNER CLASSES --------------------------

    public static class Cell extends SimplePanel {
        private String style;

        Cell(String style, IsWidget w) {
            super(DOM.createElement("td"));
            setWidget(w);
            this.style = style;
        }

        Cell(String style, String w) {
            super(DOM.createElement("td"));
            getElement().setInnerHTML(w);
            this.style = style;
        }

        public Cell(IsWidget widget) {
            this(null, widget);
        }

        public Cell(String w) {
            this(null, w);
        }

        public Cell colspan(int i) {
            getElement().setAttribute("colspan", String.valueOf(i));
            return this;
        }

        public String getStyle() {
            return style;
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

                if (tc.getStyle() == null) {
                    tc.addStyleName("cell");
                    tc.addStyleName("cell" + i);
                } else {
                    tc.addStyleName(tc.getStyle());
                }

                add(tc);
            }
        }

        private Row() {
            setElement(DOM.createElement("tr"));
        }
    }
}
