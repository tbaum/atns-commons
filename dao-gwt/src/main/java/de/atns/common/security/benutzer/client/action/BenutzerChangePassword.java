package de.atns.common.security.benutzer.client.action;

import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.shared.Action;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerChangePassword implements Action<UserPresentation> {

    private String pass;

    public BenutzerChangePassword() {
    }

    public BenutzerChangePassword(final String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }
}
