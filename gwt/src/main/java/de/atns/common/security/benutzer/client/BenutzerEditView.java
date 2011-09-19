package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import de.atns.common.security.benutzer.client.action.BenutzerUpdate;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerEditView extends BenutzerDetailView implements BenutzerEditPresenter.Display {
// ------------------------------ FIELDS ------------------------------

    private long id;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------


    public void setData(final BenutzerPresentation p, final boolean isAdmin) {
        this.id = p.getId();
        login.setValue(p.getLogin());
        email.setValue(p.getEmail());
        rollen.setValue(p.getRollen());
        admin.setValue(p.isAdmin());
        admin.setEnabled(!isAdmin);
        passwort1.setValue("");
        passwort2.setValue("");
        name.setValue(p.getName());
        updButton();
    }

    @Override public HandlerRegistration forSafe(final ClickHandler handler) {
        return speichern.addClickHandler(handler);
    }

    public BenutzerUpdate getData() {
        return new BenutzerUpdate(id, admin.getValue(), login.getValue(), passwort1.getValue(), email.getValue(),
                name.getValue(), rollen.getValue());
    }

// -------------------------- OTHER METHODS --------------------------

    protected void updButton() {
        final String p1 = passwort1.getValue();
        final String p2 = passwort2.getValue();
        speichern.setEnabled((p1.isEmpty() && p2.isEmpty()) || (p1.length() > 5 && p1.equals(p2)));
    }
}
