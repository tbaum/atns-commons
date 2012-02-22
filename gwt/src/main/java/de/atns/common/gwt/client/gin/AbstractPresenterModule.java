package de.atns.common.gwt.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.WidgetPresenter;

public abstract class AbstractPresenterModule extends AbstractGinModule {

    protected <D extends IsWidget> void bindPresenter(
            final Class<? extends WidgetPresenter<D>> presenter, final Class<D> display,
            final Class<? extends D> displayImpl) {
        bind(presenter).in(Singleton.class);
        bindDisplay(display, displayImpl);
    }

    protected <D extends IsWidget> void bindDisplay(final Class<D> display, final Class<? extends D> displayImpl) {
        bind(display).to(displayImpl);
    }
}
