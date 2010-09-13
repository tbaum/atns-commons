package de.atns.common.security.benutzer.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class BenutzerUpdateEvent extends GwtEvent<BenutzerUpdateEventHandler> {
// ------------------------------ FIELDS ------------------------------

    private final BenutzerPresentation benutzer;

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerUpdateEvent(final BenutzerPresentation benutzer) {
        this.benutzer = benutzer;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public BenutzerPresentation getBenutzer() {
        return benutzer;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void dispatch(final BenutzerUpdateEventHandler handler) {
        handler.onUpdate(this);
    }

    @Override
    public Type<BenutzerUpdateEventHandler> getAssociatedType() {
        return BenutzerUpdateEventHandler.TYPE;
    }
}