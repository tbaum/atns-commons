package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.*;
import de.atns.common.gwt.client.event.PageUpdateEvent;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.gwt.client.model.StandardFilter;
import de.atns.common.security.benutzer.client.action.BenutzerList;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEventHandler;
import de.atns.common.security.benutzer.client.gin.BenutzerInjector;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import de.atns.common.security.client.Callback;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import static de.atns.common.security.client.DefaultCallback.callback;


/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class BenutzerPresenter extends DefaultWidgetPresenter<BenutzerPresenter.Display> implements ListPresenter {
// ------------------------------ FIELDS ------------------------------

    private final DispatchAsync dispatcher;
    private final BenutzerEditPresenter editPresenter;
    private final PagePresenter pagePresenter;
    private final BenutzerInjector injector;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerPresenter(final Display display, final EventBus bus, final DispatchAsync dispatcher,
                             final PagePresenter pagePresenter, final BenutzerEditPresenter editPresenter,
                             final BenutzerInjector injector) {
        super(display, bus);
        this.dispatcher = dispatcher;
        this.pagePresenter = pagePresenter;
        this.editPresenter = editPresenter;
        this.injector = injector;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void onBindInternal() {
        registerHandler(eventBus.addHandler(BenutzerUpdateEventHandler.TYPE,
                new BenutzerUpdateEventHandler() {
                    @Override public void onUpdate(final BenutzerUpdateEvent event) {
                        updateList();
                    }
                }));

        registerHandler(display.forSuche(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                updateList();
            }
        }));
        registerHandler(display.forPressEnter(new EnterKeyPressHandler() {
            @Override protected void onEnterPressed() {
                updateList();
            }
        }));

        registerHandler(display.forNeu(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                injector.getBenutzerCreatePresenter().bind();
            }
        }));

        pagePresenter.bind(this);
        display.setPagePresenter(pagePresenter);

        updateList();
    }

    @Override public void updateList() {
        dispatcher.execute(display.getData(),
                callback(dispatcher, eventBus, display, new Callback<ListPresentation<BenutzerPresentation>>() {
                    @Override public void callback(final ListPresentation<BenutzerPresentation> result) {
                        display.reset();
                        for (final BenutzerPresentation g : result.getEntries()) {
                            registerHandler(display.addRow(g, new ClickHandler() {
                                @Override public void onClick(final ClickEvent event) {
                                    editPresenter.bind(g);
                                }
                            }));
                        }
                        eventBus.fireEvent(
                                new PageUpdateEvent(BenutzerPresenter.this, result.getTotal(), result.getStart()));
                    }
                }));
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