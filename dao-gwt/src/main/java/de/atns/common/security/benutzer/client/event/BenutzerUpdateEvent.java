package de.atns.common.security.benutzer.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.atns.common.security.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class BenutzerUpdateEvent extends GwtEvent<BenutzerUpdateEventHandler> {

    private final UserPresentation benutzer;

    public BenutzerUpdateEvent(final UserPresentation benutzer) {
        this.benutzer = benutzer;
    }

    public UserPresentation getBenutzer() {
        return benutzer;
    }

    @Override protected void dispatch(final BenutzerUpdateEventHandler handler) {
        handler.onUpdate(this);
    }

    @Override public Type<BenutzerUpdateEventHandler> getAssociatedType() {
        return BenutzerUpdateEventHandler.TYPE;
    }
}
