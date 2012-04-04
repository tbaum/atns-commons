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

    public static Table table(final Row... w) {
        final Table fp = new Table();
        fp.add(w);
        return fp;
    }

    public void add(final Row... w) {
        for (final Row row : w) {
            add(row);
        }
    }

    public static Table table(final String style, final Row... w) {
        final Table fp = table(w);
        fp.addStyleName(style);
        return fp;
    }

    public static Row head(final Object... w) {
        final Row fp = new Row();
        fp.setStyleName("header");
        fp.add(w);
        return fp;
    }

    public static Row row(final Object... w) {
        final Row fp = new Row();
        fp.setStyleName("row");
        fp.add(w);
        return fp;
    }

    public static Cell cell(final IsWidget w) {
        return new Cell(w);
    }

    public static Cell cell(final String s) {
        return new Cell(s);
    }

    public static Cell cell(final String style, final IsWidget w) {
        return new Cell(style, w);
    }

    public static Cell cell(final String style, final String s) {
        return new Cell(style, s);
    }

    Table() {
        setElement(DOM.createElement("table"));
        setCellspacing(0);
    }

    public void setCellspacing(int cellspacing) {
        getElement().setAttribute("cellspacing", Integer.toString(cellspacing));
    }

    public Table(int cellspacing) {
        setElement(DOM.createElement("table"));
        setCellspacing(cellspacing);
    }

    @Override public void add(final Widget child) {
        add(child, getElement());
    }

    public static class Cell extends SimplePanel {

        private String style;

        Cell(final String style, final IsWidget w) {
            super(DOM.createElement("td"));
            setWidget(w);
            this.style = style;
        }

        Cell(final String style, final String w) {
            super(DOM.createElement("td"));
            getElement().setInnerHTML(w);
            this.style = style;
        }

        public Cell(final IsWidget widget) {
            this(null, widget);
        }

        public Cell(final String w) {
            this(null, w);
        }

        public Cell() {
            this(null, "");
        }

        public Cell colspan(final int i) {
            setColspan(i);
            return this;
        }

        public void setColspan(int i) {
            getElement().setAttribute("colspan", String.valueOf(i));
        }

        public String getStyle() {
            return style;
        }
    }

    public static class Row extends ComplexPanel {

        private int columnCount = 0;

        @Override public void add(final Widget w) {
            add(w, getElement());
        }

        @Override public void clear() {
            super.clear();
            columnCount = 0;
        }

        public void add(final Object... w) {
            for (int i = 0, w1Length = w.length; i < w1Length; i++) {
                final Object widget = w[i];
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
                    tc.addStyleName("cell" + columnCount);
                } else {
                    tc.addStyleName(tc.getStyle());
                }

                add(tc);
                columnCount++;
            }
        }

        private Row() {
            setElement(DOM.createElement("tr"));
        }
    }
}
