package de.atns.common.gwt.app.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author tbaum
 * @since 04.11.10
 */
public class StartPlace extends Place {
// ------------------------------ FIELDS ------------------------------

    public static final StartPlace START = new StartPlace();

// -------------------------- INNER CLASSES --------------------------

    @Prefix("start")
    public static class Tokenizer implements PlaceTokenizer<StartPlace> {
        public StartPlace getPlace(String token) {
            return START;
        }

        public String getToken(StartPlace place) {
            return null;
        }
    }
}
