package de.atns.common.gwt.client.gin;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceManager;

/**
 * @author tbaum
 * @since 14.06.2010
 */
public interface SharedServices {
// -------------------------- OTHER METHODS --------------------------

    DispatchAsync getDispatchAsync();

    EventBus getEventBus();

    PlaceManager getPlaceManager();
}
