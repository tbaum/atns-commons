package de.atns.common.security.benutzer.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.PagePresenter;
import de.atns.common.gwt.client.PageView;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.gwt.client.gin.SharedServicesAware;
import de.atns.common.security.benutzer.client.*;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.Display;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.Presenter;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerModule extends AbstractPresenterModule {
// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void configure() {
        bind(SharedServicesAware.class).to(BenutzerModule.SharedServicesAdapter.class);
        bind(SharedServices.class).toProvider(BenutzerModule.SharedServicesAdapter.class);

        bindPresenter(BenutzerPresenter.class, BenutzerPresenter.Display.class, BenutzerView.class);
        bindPresenter(BenutzerCreatePresenter.class, BenutzerCreatePresenter.Display.class, BenutzerCreateView.class);
        bindPresenter(BenutzerEditPresenter.class, BenutzerEditPresenter.Display.class, BenutzerEditView.class);
        bindPresenter(BenutzerChangePasswordPresenter.class, BenutzerChangePasswordPresenter.Display.class,
                BenutzerChangePasswordView.class);

        bindDisplay(PagePresenter.Display.class, PageView.class);
    }

    protected <D extends Display> void bindPresenter(Class<? extends Presenter> presenter, Class<D> display,
                                                     Class<? extends D> displayImpl) {
        bind(presenter).in(Singleton.class);
        bindDisplay(display, displayImpl);
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

        public SharedServicesAdapter() {
            GWT.log("Construct " + this, null);
        }

        public void setSharedServices(SharedServices services) {
            this.services = services;
        }

        public SharedServices get() {
            return services;
        }
    }
}
