package de.atns.common.gwt;

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

    public static ExtendedFlexTable table() {
        return new ExtendedFlexTable();
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private ExtendedFlexTable() {
        table.setCellPadding(0);
        table.setCellSpacing(0);
        table.setBorderWidth(1);
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

    public ExtendedFlexTable nextRow() {
        return nextRow(false);
    }

    public ExtendedFlexTable nextRow(boolean noadd) {
        row++;
        if (noadd) rowx++;
        col = 0;
        return this;
    }

    public ExtendedFlexTable width(int w) {
        table.getFlexCellFormatter().setWidth(row, col - 1, w + "px");
        return this;
    }
}
