package de.atns.common.gwt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.atns.common.gwt.client.ListPresenter;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class PageUpdateEvent extends GwtEvent<PageUpdateEventHandler> {
// ------------------------------ FIELDS ------------------------------

    private int total;
    private int start;
    private ListPresenter presenter;

// --------------------------- CONSTRUCTORS ---------------------------

    public PageUpdateEvent(final ListPresenter presenter, final int total, final int start) {
        this.total = total;
        this.start = start;
        this.presenter = presenter;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public ListPresenter getPresenter() {
        return presenter;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void dispatch(final PageUpdateEventHandler handler) {
        handler.onUpdate(this);
    }

    @Override
    public Type<PageUpdateEventHandler> getAssociatedType() {
        return PageUpdateEventHandler.TYPE;
    }
}