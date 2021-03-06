package de.atns.common.gwt.app.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.extensions.security.RoleConverter;
import com.google.web.bindery.event.shared.EventBus;
import de.atns.common.gwt.client.Loader;
import de.atns.common.gwt.client.gin.AbstractPresenterModule;
import de.atns.common.gwt.client.gin.AppShell;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.gwt.client.window.EventSerializer;
import de.atns.common.gwt.client.window.MasterWindowEventBus;
import de.atns.common.gwt.client.window.PopupWindowEventBus;
import de.atns.common.gwt.client.window.TransportAware;
import de.atns.common.security.client.model.SecurityRolePresentation;
import de.atns.common.security.shared.ApplicationState;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.gin.StandardDispatchModule;

/**
 * @author tbaum
 * @since 22.11.10
 */
public abstract class ApplicationPresenterModule extends AbstractPresenterModule {

    private static final TransportAware NOOP_TRANSPORT = new TransportAware() {
        @Override public void fromJson(JSONValue data) {
        }

        @Override public JSONValue toJson() {
            return JSONNull.getInstance();
        }
    };

    protected void bindApplication(final Class<? extends ApplicationActivityMapper> activityMapperClass,
                                   final Class<? extends PlaceHistoryMapper> historyMapperClass,
                                   final Class<? extends Navigation> navigationClass) {
        bind(ActivityMapper.class).to(activityMapperClass).in(Singleton.class);
        bind(PlaceHistoryMapper.class).to(historyMapperClass).in(Singleton.class);
        bind(Navigation.class).to(navigationClass);
    }

    @Override protected final void configure() {
        install(new StandardDispatchModule());

        bindDisplay(ApplicationPresenter.Display.class, ApplicationShell.class);
        bind(ApplicationShell.class).in(Singleton.class);
        requestStaticInjection(ApplicationActivityMapper.class);
        requestStaticInjection(Navigation.class);
        requestStaticInjection(SecurityRolePresentation.class);

        configureApplication();
    }

    protected abstract void configureApplication();

    @Provides @Singleton protected EventBus eventBus(final EventSerializer eventSerializer) {
        if (PopupWindowEventBus.isRunningInPopup()) {
            return new PopupWindowEventBus(eventSerializer);
        } else {
            return new MasterWindowEventBus(eventSerializer);
        }
    }

    @Provides @Singleton public EventSerializer eventSerializer() {
        return new EventSerializer() {
            @Override protected TransportAware createClass(String clazz) {
                return NOOP_TRANSPORT;
            }
        };
    }

    @Provides @Singleton ActivityManager getActivityManager(final ActivityMapper mapper, final EventBus eventBus,
                                                            final ApplicationShell shell) {
        final ActivityManager activityManager = new ActivityManager(mapper, eventBus);
        activityManager.setDisplay(shell.getContentWidget());
        return activityManager;
    }

    @Provides @Singleton PlaceController getPlaceController(final EventBus eventBus) {
        return new PlaceController(eventBus);
    }

    @Provides @Singleton PlaceHistoryHandler getPlaceHistoryHandler(final PlaceHistoryMapper mapper,
                                                                    final EventBus eventBus,
                                                                    final PlaceController placeController,
                                                                    final @ApplicationDefaultPlace Place defaultPlace) {
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(mapper);
        historyHandler.register(placeController, eventBus, defaultPlace);
        return historyHandler;
    }

    @Provides @Singleton SharedServices getSharedServices(final DispatchAsync dispatcher, final EventBus eventbus,
                                                          final ApplicationShell appShell,
                                                          final RoleConverter roleConverter,
                                                          final Loader loader,
                                                          final ApplicationState applicationState) {
        return new SharedServices() {
            @Override public DispatchAsync dispatcher() {
                return dispatcher;
            }

            @Override public ApplicationState applicationState() {
                return applicationState;
            }

            @Override public EventBus eventBus() {
                return eventbus;
            }

            @Override public AppShell appShell() {
                return appShell;
            }

            @Override public Loader loader() {
                return loader;
            }

            @Override public RoleConverter roleConverter() {
                return roleConverter;
            }
        };
    }
}
