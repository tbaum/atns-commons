package de.atns.common.security.benutzer.client.action;

import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.shared.Action;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerCreate implements Action<UserPresentation> {

    private UserPresentation presentation;

    public BenutzerCreate() {
    }

    public BenutzerCreate(UserPresentation presentation) {
        this.presentation = presentation;
    }

    public UserPresentation getPresentation() {
        return presentation;
    }
}
