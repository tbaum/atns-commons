package de.atns.common.security.benutzer.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.atns.common.security.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class BenutzerUpdateEvent extends GwtEvent<BenutzerUpdateEventHandler> {
// ------------------------------ FIELDS ------------------------------

    private final UserPresentation benutzer;

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerUpdateEvent(final UserPresentation benutzer) {
        this.benutzer = benutzer;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public UserPresentation getBenutzer() {
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
