package de.atns.common.gwt.app.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.requestfactory.shared.RequestFactory;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.gin.AbstractPresenterModule;
import de.atns.common.gwt.client.gin.SharedServices;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.gin.StandardDispatchModule;

/**
 * @author tbaum
 * @since 22.11.10
 */
public abstract class ApplicationPresenterModule extends AbstractPresenterModule {
// -------------------------- OTHER METHODS --------------------------

    protected void bindApplication(Class<? extends ApplicationActivityMapper> activityMapperClass,
                                   Class<? extends PlaceHistoryMapper> historyMapperClass,
                                   Class<? extends Navigation> navigationClass) {
        install(new StandardDispatchModule());
        bind(ActivityMapper.class).to(activityMapperClass).in(Singleton.class);
        bind(historyMapperClass).in(Singleton.class);

        bind(Navigation.class).to(navigationClass);
        
        requestStaticInjection(Navigation.class);
    }

    @Override protected final void configure() {
        bind(ApplicationShell.class).in(Singleton.class);
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

        configureApplication();
    }

    protected abstract void configureApplication();

    @Provides @Singleton ActivityManager getActivityManager(final ActivityMapper mapper, final EventBus eventBus,
                                                            final ApplicationShell shell) {
        final ActivityManager activityManager = new ActivityManager(mapper, eventBus);
        activityManager.setDisplay(shell.getPanel());
        return activityManager;
    }

    @Provides @Singleton PlaceController getPlaceController(final EventBus eventBus) {
        return new PlaceController(eventBus);
    }

    @Provides @Singleton PlaceHistoryHandler getPlaceHistoryHandler(final PlaceHistoryMapper mapper,
                                                                    final EventBus eventBus,
                                                                    final PlaceController placeController) {
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(mapper);
        historyHandler.register(placeController, eventBus, StartPlace.START);
        return historyHandler;
    }

    @Provides @Singleton SharedServices getSharedServices(final DispatchAsync dispatcher, final EventBus eventbus,
                                                          final RequestFactory requestFactory) {
        return new SharedServices() {
            @Override public DispatchAsync dispatcher() {
                return dispatcher;
            }

            @Override public EventBus eventBus() {
                return eventbus;
            }

            @Override public RequestFactory requestFactory() {
                return requestFactory;
            }
        };
    }
}
