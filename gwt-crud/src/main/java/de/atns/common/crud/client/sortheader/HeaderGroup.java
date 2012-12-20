package de.atns.common.crud.client.sortheader;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import de.atns.common.gwt.client.Table;

import java.util.ArrayList;

public class HeaderGroup<FIELD extends OrderField> implements HasValue<SortColumn<FIELD>> {
    private final SortColumn<FIELD> defaultOrder;

    public HeaderGroup(SortColumn<FIELD> defaultOrder) {
        this.defaultOrder = defaultOrder;
        sortColumn = defaultOrder;
    }

    private final ArrayList<SortHeading> headers = new ArrayList<SortHeading>();
    private SortColumn<FIELD> sortColumn;


    private final ArrayList<ValueChangeHandler<SortColumn<FIELD>>> handlers =
            new ArrayList<ValueChangeHandler<SortColumn<FIELD>>>();
    private final Table.Row headerRow = Table.head();

    public Table.Row getHeaderRow() {
        return headerRow;
    }

    @Override public void fireEvent(final GwtEvent<?> gwtEvent) {
        if (gwtEvent instanceof ValueChangeEvent) {
            final ValueChangeEvent event = (ValueChangeEvent) gwtEvent;
            for (final ValueChangeHandler<SortColumn<FIELD>> header : handlers) {
                // TODO check
                header.onValueChange(event);
            }
        }
    }

    @Override public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<SortColumn<FIELD>> handler) {
        handlers.add(handler);
        return new HandlerRegistration() {
            @Override public void removeHandler() {
                handlers.remove(handler);
            }
        };
    }

    @Override public SortColumn<FIELD> getValue() {
        return sortColumn;
    }

    @Override public void setValue(final SortColumn<FIELD> sortColumn) {
        setValue(sortColumn, false);
    }

    public Widget addHeader(final String s) {
        final Label label = new Label(s);
        headerRow.add(new Object[]{label});
        return label;
    }

    public SortHeading addHeader(final String name, final FIELD field) {
        final SortHeading widget = new SortHeading(name);
        headers.add(widget);

        widget.addValueChangeHandler(new ValueChangeHandler<OrderField.Sort>() {
            @Override public void onValueChange(final ValueChangeEvent<OrderField.Sort> sortValueChangeEvent) {
                for (final SortHeading sortHeading : headers) {
                    if (sortHeading != widget) {
                        sortHeading.setSort(OrderField.Sort.NONE);
                    }
                }
                setValue(new SortColumn<FIELD>(field, sortValueChangeEvent.getValue()), true);
            }
        });

        headerRow.add(new Object[]{widget});
        return widget;
    }


    public void clear() {
        headerRow.clear();
        handlers.clear();
        headers.clear();
        sortColumn = defaultOrder;
    }

    @Override public void setValue(final SortColumn<FIELD> sortColumn, final boolean b) {
        final boolean shouldFire = this.sortColumn == null || !this.sortColumn.equals(sortColumn);

        this.sortColumn = new SortColumn<FIELD>(sortColumn.field, sortColumn.value);

        if (b && shouldFire) {
            HeaderGroup.this.fireEvent(new ValueChangeEvent<SortColumn>(sortColumn) {
            });
        }
    }
}
