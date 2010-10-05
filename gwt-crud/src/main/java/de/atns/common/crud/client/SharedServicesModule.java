package de.atns.common.crud.client;

import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.gwt.client.gin.SharedServicesAware;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.Display;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.Presenter;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class SharedServicesModule extends AbstractPresenterModule {
// -------------------------- OTHER METHODS --------------------------

    protected <D extends Display> void bindPresenter(Class<? extends Presenter> presenter, Class<D> display,
                                                     Class<? extends D> displayImpl) {
        bind(presenter).in(Singleton.class);
        bindDisplay(display, displayImpl);
    }

    @Override protected void configure() {
        bind(SharedServicesAware.class).to(SharedServicesAdapter.class);
        bind(SharedServices.class).toProvider(SharedServicesAdapter.class);

        bindDisplay(PagePresenter.Display.class, PageView.class);
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
