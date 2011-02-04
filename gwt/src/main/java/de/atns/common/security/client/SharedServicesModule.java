package de.atns.common.security.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.RequestFactory;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.gin.AbstractPresenterModule;
import de.atns.common.gwt.client.gin.AppShell;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.gwt.client.gin.SharedServicesAware;
import de.atns.common.gwt.client.window.MasterWindowEventBus;
import de.atns.common.gwt.client.window.PopupWindowEventBus;
import net.customware.gwt.dispatch.client.DispatchAsync;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class SharedServicesModule extends AbstractPresenterModule {
// -------------------------- OTHER METHODS --------------------------

    @Provides public AppShell appshell(final SharedServices sharedServices) {
        return sharedServices.appShell();
    }

    @Override protected void configure() {
        bind(SharedServicesAware.class).to(SharedServicesAdapter.class);
        bind(SharedServices.class).toProvider(SharedServicesAdapter.class);

        requestStaticInjection(Callback.class);
    }

    @Provides public DispatchAsync dispatchAsync(final SharedServices sharedServices) {
        return sharedServices.dispatcher();
    }

    @Provides public EventBus eventBus(final SharedServices sharedServices) {
        return sharedServices.eventBus();
    }

    @Provides MasterWindowEventBus masterWindowEventBus(final SharedServices sharedServices) {
        EventBus eventBus = sharedServices.eventBus();
        if (!(eventBus instanceof MasterWindowEventBus)) {
            throw new RuntimeException("nicht im popup-fenter verfügbar!!");
        }
        return (MasterWindowEventBus) eventBus;
    }

    @Provides PopupWindowEventBus popupWindowEventBus(final SharedServices sharedServices) {
        EventBus eventBus = sharedServices.eventBus();
        if (!(eventBus instanceof PopupWindowEventBus)) {
            throw new RuntimeException("nicht im master-fenter verfügbar!!");
        }
        return (PopupWindowEventBus) eventBus;
    }

    @Provides public RequestFactory requestFactory(final SharedServices sharedServices) {
        return sharedServices.requestFactory();
    }

// -------------------------- INNER CLASSES --------------------------

    @Singleton
    public static class SharedServicesAdapter implements Provider<SharedServices>, SharedServicesAware {
        private SharedServices services;

        public void setSharedServices(final SharedServices services) {
            this.services = services;
        }

        public SharedServices get() {
            return services;
        }
    }
}
