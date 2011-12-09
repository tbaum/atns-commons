package de.atns.common.security.benutzer.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import de.atns.common.crud.client.ListDisplay;
import de.atns.common.crud.client.ListPresenter;
import de.atns.common.crud.client.event.LoadListEventHandler;
import de.atns.common.gwt.client.gin.PlacePresenter;
import de.atns.common.gwt.client.model.StandardFilter;
import de.atns.common.security.benutzer.client.action.BenutzerList;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEventHandler;
import de.atns.common.security.client.model.UserPresentation;


/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class BenutzerPresenter extends ListPresenter<BenutzerPresenter.Display, UserPresentation>
        implements PlacePresenter<BenutzerPlace> {
// ------------------------------ FIELDS ------------------------------

    private static final GwtEvent.Type<LoadListEventHandler<UserPresentation>> LIST_EVENT =
            new GwtEvent.Type<LoadListEventHandler<UserPresentation>>();
    private final BenutzerEditPresenter editPresenter;
    private final BenutzerCreatePresenter benutzerCreatePresenter;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerPresenter(final BenutzerEditPresenter editPresenter,
                             BenutzerCreatePresenter benutzerCreatePresenter) {
        this.editPresenter = editPresenter;
        this.benutzerCreatePresenter = benutzerCreatePresenter;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface PlacePresenter ---------------------

    @Override public Activity updateForPlace(final BenutzerPlace place) {
        return this;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override public GwtEvent.Type<LoadListEventHandler<UserPresentation>> _listEvent() {
        return LIST_EVENT;
    }

    protected String bindRow(final UserPresentation g, final String lastValue) {
        registerHandler(display.addRow(g, new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                editPresenter.bind(g);
            }
        }));

        return lastValue;
    }

    protected BenutzerList createLoadAction() {
        return new BenutzerList(display.getFilter(), pagePresenter.getStartEntry(), pagePresenter.getPageRange());
    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(display.forNeu(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                benutzerCreatePresenter.bind();
            }
        }));

        registerHandler(eventBus.addHandler(BenutzerUpdateEventHandler.TYPE,
                new BenutzerUpdateEventHandler() {
                    @Override public void onUpdate(final BenutzerUpdateEvent event) {
                        updateList();
                    }
                }));
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends ListDisplay<UserPresentation> {
        HandlerRegistration forNeu(ClickHandler clickHandler);

        HandlerRegistration forSuche(ClickHandler clickHandler);

        StandardFilter getFilter();

        HandlerRegistration addRow(UserPresentation auftrag, ClickHandler edit);

        HandlerRegistration forPressEnter(KeyPressHandler pressHandler);
    }
}
