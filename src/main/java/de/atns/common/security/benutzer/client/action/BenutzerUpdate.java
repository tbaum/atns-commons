package de.atns.common.security.benutzer.client.action;

import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.shared.Action;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerUpdate implements Action<UserPresentation> {

    private UserPresentation presentation;

    public BenutzerUpdate() {
    }

    public BenutzerUpdate(UserPresentation presentation) {
        this.presentation = presentation;
    }

    public UserPresentation getPresentation() {
        return presentation;
    }
}
