package de.atns.common.crud.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.crud.client.event.LoadListEventHandler;
import de.atns.common.gwt.client.DefaultWidgetPresenter;
import de.atns.common.gwt.client.ErrorWidgetDisplay;
import de.atns.common.gwt.client.ListPresenter;
import de.atns.common.gwt.client.event.PageUpdateEvent;
import de.atns.common.gwt.client.event.PageUpdateEventHandler;
import de.atns.common.gwt.client.model.ListPresentation;


/**
 * @author tbaum
 * @since 24.10.2009
 */
public class PagePresenter extends DefaultWidgetPresenter<PagePresenter.Display> {
// ------------------------------ FIELDS ------------------------------

    private static final int PAGING = 2;
    private ListPresenter parentPresenter;
    private int startEntry = 0;
    private int range = 20;

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getStartEntry() {
        return startEntry;
    }

// -------------------------- OTHER METHODS --------------------------

    public void bind(ListPresenter parentPresenter) {
        this.parentPresenter = parentPresenter;
        bind();
    }

    public <T extends IsSerializable> void bind(final ListPresenter<T> parentPresenter,
                                                final GwtEvent.Type<LoadListEventHandler<T>> type) {
        bind(parentPresenter);
        registerHandler(eventBus.addHandler(type, new LoadListEventHandler<T>() {
            @Override
            public void onLoad(final ListPresentation<T> result, final Object source) {
                if (parentPresenter.equals(source)) {
                    display.reset();
                    createButtons(result.getTotal(), result.getStart());
                }
            }
        }));
    }

    private void createButtons(final int total, int start) {
        startEntry = start;

        final int sites = (int) Math.ceil((double) total / range);

        start = startEntry / range + 1;

        addDisplayButton(start, range, 1);

        if (sites > 1) {
            if (start - PAGING > 2) {
                display.addDots();
            }

            for (int i = start - PAGING; i <= start + PAGING; i++) {
                if (i > 1 && i < sites) {
                    addDisplayButton(start, range, i);
                }
            }
            if (start + PAGING < sites - 1) {
                display.addDots();
            }

            addDisplayButton(start, range, sites);
        }
    }

    private void addDisplayButton(final int start, final int range, final int startX) {
        registerHandler(display.addSeitenButton(startX, new PageClickHandler(startX, range), start == startX));
    }

    public void firstPage() {
        startEntry = 0;
    }

    public int getPageRange() {
        return range;
    }

    @Override
    public void onBind() {
        super.onBind();

        registerHandler(this.display.addLengthButton(new ChangeHandler() {
            @Override
            public void onChange(final ChangeEvent event) {
                range = PagePresenter.this.display.selectedRange();
                startEntry = (startEntry / range) * range;
                parentPresenter.updateList();
            }
        }, range, 20, 50, 100));

        registerHandler(eventBus.addHandler(PageUpdateEventHandler.TYPE, new PageUpdateEventHandler() {
            @Override
            public void onUpdate(final PageUpdateEvent updateEvent) {
                final ListPresenter source = updateEvent.getPresenter();
                if (parentPresenter.equals(source)) {
                    display.reset();
                    createButtons(updateEvent.getTotal(), updateEvent.getStart());
                }
            }
        }));
        startEntry = 0;
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends ErrorWidgetDisplay {
        void reset();

        HandlerRegistration addSeitenButton(int site, ClickHandler clickHandler, boolean active);

        void addDots();

        HandlerRegistration addLengthButton(ChangeHandler handler, int active, int... range);

        int selectedRange();
    }

    private class PageClickHandler implements ClickHandler {
        private final int startX;
        private final int range;

        public PageClickHandler(final int startX, final int range) {
            this.startX = startX;
            this.range = range;
        }

        @Override
        public void onClick(final ClickEvent event) {
            startEntry = (startX - 1) * range;
            parentPresenter.updateList();
        }
    }
}