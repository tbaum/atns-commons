package de.atns.common.gwt.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.binder.GinLinkedBindingBuilder;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.gwt.client.WidgetPresenter;

import static com.google.inject.name.Names.named;

public abstract class AbstractPresenterModule extends AbstractGinModule {
// --------------------------- CONSTRUCTORS ---------------------------

    public AbstractPresenterModule() {
        super();
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Convenience method for binding a type to a {@link com.google.inject.name.Named} attribute. Use
     * it something like this:
     * <p/>
     * <pre>
     * bindNamed( MyType.class, &quot;Foo&quot; ).to( MyImplementation.class );
     * </pre>
     *
     * @param <T>   The type.
     * @param type  The type.
     * @param named The string to name with.
     * @return the binding builder.
     */
    protected <T> GinLinkedBindingBuilder<T> bindNamed(Class<T> type, String named) {
        return bind(type).annotatedWith(named(named));
    }

    /**
     * Convenience method for binding a presenter as well as it's display.
     *
     * @param <D>         The display type.
     * @param presenter   The presenter.
     * @param display     The display type.
     * @param displayImpl The display implementation.
     */
    protected <D extends WidgetDisplay> void bindPresenter(Class<? extends WidgetPresenter<D>> presenter, Class<D> display,
                                                           Class<? extends D> displayImpl) {
        bind(presenter).in(Singleton.class);
        bindDisplay(display, displayImpl);
    }

    /**
     * Convenience method for binding a display implementation.
     *
     * @param <D>         The display interface type
     * @param display     The display interface
     * @param displayImpl The display implementation
     */
    protected <D extends WidgetDisplay> void bindDisplay(Class<D> display, Class<? extends D> displayImpl) {
        bind(display).to(displayImpl);
    }
}
