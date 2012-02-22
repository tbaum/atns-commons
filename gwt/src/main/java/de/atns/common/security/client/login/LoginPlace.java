package de.atns.common.security.client.login;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author tbaum
 * @since 18.11.10
 */
public class LoginPlace extends Place {

    public static final LoginPlace ALL = new LoginPlace("");

    private final String token;

    public LoginPlace(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Prefix("login") public static class Tokenizer implements PlaceTokenizer<LoginPlace> {
        @Override public LoginPlace getPlace(final String token) {
            return new LoginPlace(token);
        }

        @Override public String getToken(final LoginPlace place) {
            return place.getToken();
        }
    }
}
