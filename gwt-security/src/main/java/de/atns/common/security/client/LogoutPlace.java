package de.atns.common.security.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author tbaum
 * @since 18.11.10
 */
public class LogoutPlace extends Place {
// ------------------------------ FIELDS ------------------------------

    public static final LogoutPlace ALL = new LogoutPlace();

// -------------------------- INNER CLASSES --------------------------

    @Prefix("logout")
    public static class Tokenizer implements PlaceTokenizer<LogoutPlace> {
        private static final String NO_ID = "n";

        public LogoutPlace getPlace(String token) {
            return new LogoutPlace();
        }

        public String getToken(LogoutPlace place) {
            return NO_ID;
        }
    }
}
