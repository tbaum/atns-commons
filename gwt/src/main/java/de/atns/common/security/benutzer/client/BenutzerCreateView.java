package de.atns.common.security.benutzer.client;

import de.atns.common.security.benutzer.client.action.BenutzerCreate;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerCreateView extends BenutzerDetailView implements BenutzerCreatePresenter.Display {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override public BenutzerCreate getData() {
        return new BenutzerCreate(admin.getValue(), login.getValue(), passwort1.getValue(), email.getValue(),
                name.getValue());
    }

// -------------------------- OTHER METHODS --------------------------

    protected void updButton() {
        final String lg = login.getValue();
        final String p1 = passwort1.getValue();
        final String p2 = passwort2.getValue();
        speichern.setEnabled(lg.length() > 2 && p1.length() > 5 && p1.equals(p2));
    }
}


