package de.atns.common.security.benutzer.client.action;

import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import net.customware.gwt.dispatch.shared.Action;


/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerChangePassword implements Action<BenutzerPresentation> {
// ------------------------------ FIELDS ------------------------------

    private String pass;

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerChangePassword() {
    }

    public BenutzerChangePassword(String pass) {
        this.pass = pass;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getPass() {
        return pass;
    }
}