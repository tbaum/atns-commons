package de.atns.common.crud.client;

import com.google.inject.Singleton;
import de.atns.common.security.client.SharedServicesModule;
import net.customware.gwt.presenter.client.Display;
import net.customware.gwt.presenter.client.Presenter;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class CrudModule extends SharedServicesModule {
// -------------------------- OTHER METHODS --------------------------

    protected <D extends Display> void bindPresenter(Class<? extends Presenter> presenter, Class<D> display,
                                                     Class<? extends D> displayImpl) {
        bind(presenter).in(Singleton.class);
        bindDisplay(display, displayImpl);
    }

    @Override protected void configure() {
        super.configure();

        bindDisplay(PagePresenter.Display.class, PageView.class);
    }
}