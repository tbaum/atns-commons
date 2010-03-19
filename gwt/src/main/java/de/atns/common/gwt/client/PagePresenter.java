package de.atns.common.gwt.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import de.atns.common.gwt.event.PageUpdateEvent;
import de.atns.common.gwt.event.PageUpdateEventHandler;
import net.customware.gwt.presenter.client.EventBus;


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

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public PagePresenter(final Display display, final EventBus bus) {
        super(display, bus);
        registerHandler(this.display.addLengthButton(new ChangeHandler() {
            @Override public void onChange(final ChangeEvent event) {
                range = PagePresenter.this.display.selectedRange();
                startEntry = (startEntry / range) * range;
                parentPresenter.updateList();
            }
        }, range, 20, 50, 100));
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getStartEntry() {
        return startEntry;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Presenter ---------------------

    public void bind() {
        registerHandler(eventBus.addHandler(PageUpdateEventHandler.TYPE, new PageUpdateEventHandler() {
            @Override public void onUpdate(final PageUpdateEvent updateEvent) {
                if (parentPresenter.equals(updateEvent.getPresenter())) {
                    int total = updateEvent.getTotal();
                    int start = updateEvent.getStart();
                    display.reset();
                    createButtons(total, start);
                }
            }
        }));
        startEntry = 0;
    }

// -------------------------- OTHER METHODS --------------------------

    public void bind(ListPresenter parentPresenter) {
        this.parentPresenter = parentPresenter;
        bind();
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

    private void createLenghtButtons() {
        registerHandler(display.addLengthButton(new ChangeHandler() {
            @Override public void onChange(final ChangeEvent event) {
                range = display.selectedRange();
                startEntry = (startEntry / range) * range;
                parentPresenter.updateList();
            }
        }, range, 20, 50, 100));
    }

    public void firstPage() {
        startEntry = 0;
    }

    public int getPageRange() {
        return range;
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

        @Override public void onClick(final ClickEvent event) {
            startEntry = (startX - 1) * range;
            parentPresenter.updateList();
        }
    }
}