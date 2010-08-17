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
import de.atns.common.security.client.DefaultCallback;
import de.atns.common.security.benutzer.client.action.BenutzerList;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEventHandler;
import de.atns.common.security.benutzer.client.model.MitarbeiterFilter;
import de.atns.common.security.benutzer.client.model.MitarbeiterPresentation;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;


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

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerPresenter(final Display display, final EventBus bus, final DispatchAsync dispatcher,
                             final PagePresenter pagePresenter, final BenutzerEditPresenter editPresenter) {
        super(display, bus);
        this.dispatcher = dispatcher;
        this.pagePresenter = pagePresenter;
        this.editPresenter = editPresenter;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void onBindInternal() {
        registerHandler(eventBus.addHandler(BenutzerUpdateEventHandler.TYPE, new BenutzerUpdateEventHandler() {
            @Override public void onUpdate(final BenutzerUpdateEvent event) {
                updateList();
            }
        }));

        registerHandler(display.forSuche(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                updateList();
            }
        }));

        final EnterKeyPressHandler pressHandler = new EnterKeyPressHandler() {
            @Override protected void onEnterPressed() {
                updateList();
            }
        };

        for (HandlerRegistration registration : display.forPressEnter(pressHandler)) {
            registerHandler(registration);
        }

        pagePresenter.bind(this);
        display.showPagePresenter(pagePresenter.getDisplay());

        updateList();
    }

    @Override public void updateList() {
        dispatcher.execute(new BenutzerList(display.getFilter(), pagePresenter.getStartEntry(), pagePresenter.getPageRange()),
                new DefaultCallback<ListPresentation<MitarbeiterPresentation>>(dispatcher, eventBus, display) {
                    @Override public void callback(final ListPresentation<MitarbeiterPresentation> result) {
                        display.clearList();
                        if (result.getEntries().isEmpty()) {
                            display.addEmptyRow();
                        } else {
                            for (final MitarbeiterPresentation g : result.getEntries()) {
                                registerHandler(display.addRow(g, new ClickHandler() {
                                    @Override public void onClick(final ClickEvent event) {
                                        editPresenter.bind(g);
                                    }
                                }));
                            }
                        }
                        eventBus.fireEvent(new PageUpdateEvent(BenutzerPresenter.this, result.getTotal(), result.getStart()));
                    }
                });
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends ErrorWidgetDisplay {
        HandlerRegistration forSuche(ClickHandler clickHandler);

        MitarbeiterFilter getFilter();

        void addEmptyRow();

        HandlerRegistration addRow(MitarbeiterPresentation auftrag, ClickHandler edit);

        void reset();

        void clearList();

        void showPagePresenter(final PagePresenter.Display presenter);

        HandlerRegistration[] forPressEnter(KeyPressHandler pressHandler);
    }
}