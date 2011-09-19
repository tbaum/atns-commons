package de.atns.common.security.benutzer.server;

import de.atns.common.security.benutzer.client.action.BenutzerCreate;
import de.atns.common.security.benutzer.client.action.BenutzerUpdate;
import de.atns.common.security.model.Benutzer;

import static de.atns.common.security.model.DefaultRoles.ADMIN;

/**
 * @author tbaum
 * @since 13.09.2010
 */
public class DefaultBenutzerRollenHandler implements BenutzerRollenHandler {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface BenutzerRollenHandler ---------------------

    @Override public void updateRollen(final Benutzer benutzer, final Object action) {
        if (action instanceof BenutzerCreate) {
            updateRollen(benutzer, (BenutzerCreate) action);
        } else if (action instanceof BenutzerUpdate) {
            updateRollen(benutzer, (BenutzerUpdate) action);
        } else {
            throw new RuntimeException("unable to handle update-action for " + action);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    private void updateRollen(final Benutzer benutzer, final BenutzerCreate action) {
        benutzer.removeRolle(benutzer.getRolle());
        benutzer.addRolle(action.getRollen().split(","));
        if (action.isAdmin()) {
            benutzer.addRolle(ADMIN);
        } else {
            benutzer.removeRolle(ADMIN);
        }
    }

    private void updateRollen(final Benutzer benutzer, final BenutzerUpdate action) {
        benutzer.removeRolle(benutzer.getRolle());
        benutzer.addRolle(action.getRollen().split(","));
        if (action.isAdmin()) {
            benutzer.addRolle(ADMIN);
        } else {
            benutzer.removeRolle(ADMIN);
        }
    }
}
