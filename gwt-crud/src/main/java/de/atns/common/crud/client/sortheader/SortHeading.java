package de.atns.common.crud.client.sortheader;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

import java.util.ArrayList;

import static com.google.gwt.dom.client.Style.Cursor.POINTER;
import static de.atns.common.crud.client.sortheader.OrderField.Sort.*;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;

public class SortHeading extends Composite implements HasValueChangeHandlers<OrderField.Sort> {

    private final Anchor label = new Anchor();
    private final Image labelUp = new Image("arrow_up.png");
    private final Image labelDown = new Image("arrow_down.png");
    private OrderField.Sort sort = NONE;

    private final ArrayList<ValueChangeHandler<OrderField.Sort>> handlers =
            new ArrayList<ValueChangeHandler<OrderField.Sort>>();

    public SortHeading() {
        setSort(NONE);
        initWidget(flowPanel(label, labelUp, labelDown));
        label.getElement().getStyle().setCursor(POINTER);
        label.addClickHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent clickEvent) {
                setSort(sort.next());

                final ValueChangeEvent<OrderField.Sort> valueChangeEvent = new ValueChangeEvent<OrderField.Sort>(sort) {
                };

                for (final ValueChangeHandler<OrderField.Sort> handler : handlers) {
                    handler.onValueChange(valueChangeEvent);
                }
            }
        });
    }

    public void setSort(final OrderField.Sort sort) {
        this.sort = sort == null ? NONE : sort;
        labelUp.setVisible(sort == DESC);
        labelDown.setVisible(sort == ASC);
    }

    public SortHeading(final String text) {
        this();
        setText(text);
    }

    public void setText(final String text) {
        label.setText(text);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<OrderField.Sort> sortValueChangeHandler) {
        handlers.add(sortValueChangeHandler);
        return new HandlerRegistration() {
            @Override public void removeHandler() {
                handlers.remove(sortValueChangeHandler);
            }
        };
    }
}
