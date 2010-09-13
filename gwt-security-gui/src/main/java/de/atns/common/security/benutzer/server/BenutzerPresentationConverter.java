package de.atns.common.security.benutzer.server;

import ch.lambdaj.function.convert.Converter;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import de.atns.common.security.model.Benutzer;

/**
 * @author tbaum
 * @since 13.09.2010
 */
class BenutzerPresentationConverter implements Converter<Benutzer, BenutzerPresentation> {
// ------------------------------ FIELDS ------------------------------

    public static final BenutzerPresentationConverter BENUTZER_CONVERTER = new BenutzerPresentationConverter();

// --------------------------- CONSTRUCTORS ---------------------------

    private BenutzerPresentationConverter() {
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------

    @Override public BenutzerPresentation convert(final Benutzer benutzer) {
        return new BenutzerPresentation(benutzer.getId(), benutzer.getLogin(), benutzer.isAdmin(), benutzer.getEmail());
    }
}
