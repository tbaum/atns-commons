package de.atns.common.security.benutzer.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class BenutzerUpdateEvent extends GwtEvent<BenutzerUpdateEventHandler> {
// ------------------------------ FIELDS ------------------------------

    private final String login;

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerUpdateEvent(final String login) {
        this.login = login;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getLogin() {
        return login;
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