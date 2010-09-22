package de.atns.common.gwt.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author tbaum
 * @since 12.02.2010
 */
public class ExtendedFlexTable {
// ------------------------------ FIELDS ------------------------------

    private final FlexTable table = new FlexTable();

    private int row = 0;
    private int rowx = 0;
    private int col = 0;

// -------------------------- STATIC METHODS --------------------------

    public static ExtendedFlexTable table(String... styles) {
        return new ExtendedFlexTable(styles);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private ExtendedFlexTable(String... styles) {
        table.setCellPadding(0);
        table.setCellSpacing(0);
        table.setBorderWidth(1);
        for (String style : styles) {
            table.addStyleName(style);
        }
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public FlexTable getTable() {
        return table;
    }

// -------------------------- OTHER METHODS --------------------------

    public ExtendedFlexTable cell(Widget widget) {
        table.setWidget(row, col++, widget);
        addStyle((row + rowx) % 2 == 1 ? "even" : "odd");
        return this;
    }

    public ExtendedFlexTable addStyle(String styleName) {
        table.getFlexCellFormatter().addStyleName(row, col - 1, styleName);
        return this;
    }

    public ExtendedFlexTable cell(Object text) {
        table.setText(row, col++, text != null ? text.toString() : "");
        addStyle((row + rowx) % 2 == 1 ? "even" : "odd");
        return this;
    }

    public ExtendedFlexTable clear() {
        table.clear();
        table.removeAllRows();
        row = 0;
        rowx = 0;
        col = 0;
        return this;
    }

    public ExtendedFlexTable colspan(final int colspan) {
        table.getFlexCellFormatter().setColSpan(row, col - 1, colspan);
        return this;
    }

    public ExtendedFlexTable height(final int height) {
        table.getFlexCellFormatter().setHeight(row, col - 1, height + "px");
        return this;
    }

    public ExtendedFlexTable nextRow() {
        return nextRow(false);
    }

    public ExtendedFlexTable nextRow(boolean noadd) {
        row++;
        if (noadd) {
            rowx++;
        }
        col = 0;
        return this;
    }

    public ExtendedFlexTable rowspan(final int rowspan) {
        table.getFlexCellFormatter().setRowSpan(row, col - 1, rowspan);
        return this;
    }

    public ExtendedFlexTable width(int w) {
        table.getFlexCellFormatter().setWidth(row, col - 1, w + "px");
        return this;
    }

    public ExtendedFlexTable widthPC(int w) {
        table.getFlexCellFormatter().setWidth(row, col - 1, w + "%");
        return this;
    }
}
