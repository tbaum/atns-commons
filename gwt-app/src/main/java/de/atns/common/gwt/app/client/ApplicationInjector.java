package de.atns.common.gwt.app.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import de.atns.common.gwt.client.gin.SharedServices;

/**
 * @author tbaum
 * @since 22.11.10
 */
public interface ApplicationInjector extends Ginjector {
// -------------------------- OTHER METHODS --------------------------

    ActivityManager activityManager();

    ApplicationShell applicationShell();

    EventBus eventBus();

    PlaceHistoryHandler placeHistoryManager();

    SharedServices sharedServices();
}
