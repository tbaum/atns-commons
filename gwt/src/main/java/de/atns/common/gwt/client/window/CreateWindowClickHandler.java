package de.atns.common.gwt.client.window;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;

/**
 * @author tbaum
 * @since 16.02.12
 */
public abstract class CreateWindowClickHandler implements ClickHandler {
// ------------------------------ FIELDS ------------------------------

    private final PlaceHistoryMapper mapper;
    private final WindowEventBus eventBus;
    private final String options;

// --------------------------- CONSTRUCTORS ---------------------------

    public CreateWindowClickHandler(WindowEventBus eventBus, PlaceHistoryMapper mapper) {
        this(eventBus, mapper, "");
    }

    public CreateWindowClickHandler(WindowEventBus eventBus, PlaceHistoryMapper mapper, String options) {
        this.eventBus = eventBus;
        this.mapper = mapper;
        this.options = options;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ClickHandler ---------------------

    @Override public void onClick(ClickEvent event) {
        String token = mapper.getToken(createPlace());
        eventBus.openWindow("#" + token, token, options);
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract Place createPlace();
}
