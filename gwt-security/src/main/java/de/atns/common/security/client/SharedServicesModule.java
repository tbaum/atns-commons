package de.atns.common.security.client;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.gin.AbstractPresenterModule;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.gwt.client.gin.SharedServicesAware;
import net.customware.gwt.dispatch.client.DispatchAsync;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class SharedServicesModule extends AbstractPresenterModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configure() {
        bind(SharedServicesAware.class).to(SharedServicesAdapter.class);
        bind(SharedServices.class).toProvider(SharedServicesAdapter.class);
    }

    @Provides
    public DispatchAsync dispatchAsync(SharedServices sharedServices) {
        return sharedServices.getDispatchAsync();
    }

    @Provides
    public EventBus eventBus(SharedServices sharedServices) {
        return sharedServices.getEventBus();
    }

// -------------------------- INNER CLASSES --------------------------

    @Singleton
    public static class SharedServicesAdapter implements Provider<SharedServices>, SharedServicesAware {
        private SharedServices services;

        public void setSharedServices(SharedServices services) {
            this.services = services;
        }

        public SharedServices get() {
            return services;
        }
    }
}
