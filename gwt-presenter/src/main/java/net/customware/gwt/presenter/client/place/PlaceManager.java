package net.customware.gwt.presenter.client.place;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.customware.gwt.presenter.client.EventBus;

@Singleton
public class PlaceManager implements ValueChangeHandler<String>, PlaceChangedHandler, PlaceRequestHandler {
    private final EventBus eventBus;

    @Inject
    public PlaceManager(EventBus eventBus) {
        this.eventBus = eventBus;

        // Register ourselves with the History API.
        History.addValueChangeHandler(this);

        // Listen for manual place change events.
        eventBus.addHandler(PlaceChangedEvent.getType(), this);
    }

    @Override public void onValueChange(ValueChangeEvent<String> event) {
        try {
            eventBus.fireEvent(new PlaceRequestEvent(PlaceRequest.fromString(event.getValue()), true));
        } catch (PlaceParsingException e) {
            e.printStackTrace();
        }
    }

    @Override public void onPlaceChange(PlaceChangedEvent event) {
        newPlace(event.getRequest());
    }

    private void newPlace(PlaceRequest request) {
        History.newItem(request.toString(), false);
    }

    @Override public void onPlaceRequest(PlaceRequestEvent event) {
        if (!event.isFromHistory()) {
            newPlace(event.getRequest());
        }
    }

    public void fireCurrentPlace() {
        if (History.getToken() != null)
            History.fireCurrentHistoryState();
    }

}
