package de.atns.common.security.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author tbaum
 * @since 18.11.10
 */
public class LoginPlace extends Place {
// ------------------------------ FIELDS ------------------------------

    public static final LoginPlace ALL = new LoginPlace("");

    private final String token;

// --------------------------- CONSTRUCTORS ---------------------------

    public LoginPlace(String token) {
        this.token = token;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getToken() {
        return token;
    }

// -------------------------- INNER CLASSES --------------------------

    @Prefix("login")
    public static class Tokenizer implements PlaceTokenizer<LoginPlace> {
        public LoginPlace getPlace(String token) {
            return new LoginPlace(token);
        }

        public String getToken(LoginPlace place) {
            return place.getToken();
        }
    }
}
