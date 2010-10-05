package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.crud.client.PagePresenter;
import de.atns.common.crud.client.event.LoadListEventHandler;
import de.atns.common.gwt.client.DefaultWidgetPresenter;
import de.atns.common.gwt.client.EnterKeyPressHandler;
import de.atns.common.gwt.client.ErrorWidgetDisplay;
import de.atns.common.gwt.client.ListPresenter;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.gwt.client.model.StandardFilter;
import de.atns.common.security.benutzer.client.action.BenutzerList;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEventHandler;
import de.atns.common.security.benutzer.client.gin.BenutzerInjector;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;

import static de.atns.common.crud.client.event.LoadListEvent.eventCallback;


/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class BenutzerPresenter extends DefaultWidgetPresenter<BenutzerPresenter.Display> implements ListPresenter {
// ------------------------------ FIELDS ------------------------------

    public static final GwtEvent.Type<LoadListEventHandler<BenutzerPresentation>> TYPE =
            new GwtEvent.Type<LoadListEventHandler<BenutzerPresentation>>();

    private final BenutzerEditPresenter editPresenter;
    private final PagePresenter pagePresenter;
    private final BenutzerInjector injector;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerPresenter(final PagePresenter pagePresenter, final BenutzerEditPresenter editPresenter,
                             final BenutzerInjector injector) {
        this.pagePresenter = pagePresenter;
        this.editPresenter = editPresenter;
        this.injector = injector;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void onBind() {
        super.onBind();
        registerHandler(eventBus.addHandler(BenutzerUpdateEventHandler.TYPE,
                new BenutzerUpdateEventHandler() {
                    @Override
                    public void onUpdate(final BenutzerUpdateEvent event) {
                        updateList();
                    }
                }));

        registerHandler(display.forSuche(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                updateList();
            }
        }));
        registerHandler(display.forPressEnter(new EnterKeyPressHandler() {
            @Override
            protected void onEnterPressed() {
                updateList();
            }
        }));

        registerHandler(display.forNeu(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                injector.getBenutzerCreatePresenter().bind();
            }
        }));

        registerHandler(eventBus.addHandler(TYPE, new LoadListEventHandler<BenutzerPresentation>() {
            @Override
            public void onLoad(final ListPresentation<BenutzerPresentation> result, final Object source) {
                display.reset();
                for (final BenutzerPresentation g : result.getEntries()) {
                    registerHandler(display.addRow(g, new ClickHandler() {
                        @Override
                        public void onClick(final ClickEvent event) {
                            editPresenter.bind(g);
                        }
                    }));
                }
            }
        }));

        pagePresenter.bind(this, TYPE);
        display.setPagePresenter(pagePresenter);

        updateList();
    }

    @Override
    public void updateList() {
        dispatcher.execute(display.getData(), eventCallback(display, TYPE, this));
    }

    @Override
    protected void onUnbind() {
        super.onUnbind();
        pagePresenter.unbind();
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends ErrorWidgetDisplay {
        HandlerRegistration forNeu(ClickHandler clickHandler);

        HandlerRegistration forSuche(ClickHandler clickHandler);

        StandardFilter getFilter();

        HandlerRegistration addRow(BenutzerPresentation auftrag, ClickHandler edit);

        void setPagePresenter(final PagePresenter presenter);

        HandlerRegistration[] forPressEnter(KeyPressHandler pressHandler);

        BenutzerList getData();
    }
}