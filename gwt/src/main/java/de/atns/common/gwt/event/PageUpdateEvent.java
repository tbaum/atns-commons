package de.atns.common.gwt.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class PageUpdateEvent extends GwtEvent<PageUpdateEventHandler> {
// ------------------------------ FIELDS ------------------------------

    private int total;
    private int start;

// --------------------------- CONSTRUCTORS ---------------------------

    public PageUpdateEvent(final int total, final int start) {
        this.total = total;
        this.start = start;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

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