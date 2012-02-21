package de.atns.common.security.client.login;

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
        @Override public LogoutPlace getPlace(final String token) {
            return new LogoutPlace();
        }

        @Override public String getToken(final LogoutPlace place) {
            return "";
        }
    }
}
