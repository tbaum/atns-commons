package de.atns.common.security.benutzer.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author tbaum
 * @since 18.11.10
 */
public class BenutzerPlace extends Place {
// ------------------------------ FIELDS ------------------------------

    public static final BenutzerPlace ALL = new BenutzerPlace();

// -------------------------- INNER CLASSES --------------------------

    @Prefix("benutzer")
    public static class Tokenizer implements PlaceTokenizer<BenutzerPlace> {
        private static final String NO_ID = "n";

        @Override public BenutzerPlace getPlace(final String token) {
            return new BenutzerPlace();
        }

        @Override public String getToken(final BenutzerPlace place) {
            return NO_ID;
        }
    }
}
