package de.atns.common.gwt.app.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.web.bindery.event.shared.EventBus;
import de.atns.common.gwt.client.gin.SharedServices;

/**
 * @author tbaum
 * @since 22.11.10
 */
public interface ApplicationInjector extends Ginjector {

    ActivityManager activityManager();

    ApplicationShell applicationShell();

    EventBus eventBus();

    PlaceHistoryHandler placeHistoryManager();

    SharedServices sharedServices();

    ApplicationPresenter applicationPresenter();
}
