package de.atns.common.gwt.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.gwt.client.WidgetPresenter;

public abstract class AbstractPresenterModule extends AbstractGinModule {
// -------------------------- OTHER METHODS --------------------------

    protected <D extends WidgetDisplay> void bindPresenter(Class<? extends WidgetPresenter<D>> presenter, Class<D> display,
                                                           Class<? extends D> displayImpl) {
        bind(presenter).in(Singleton.class);
        bindDisplay(display, displayImpl);
    }

    protected <D extends WidgetDisplay> void bindDisplay(Class<D> display, Class<? extends D> displayImpl) {
        bind(display).to(displayImpl);
    }
}
