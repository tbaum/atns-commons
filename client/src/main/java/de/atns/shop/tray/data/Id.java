package de.atns.shop.tray.data;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.RequestScoped;
import static de.atns.shop.tray.Util.extractParameter;

import java.util.Map;

@RequestScoped public class Id {
// ------------------------------ FIELDS ------------------------------

    private final String id;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public Id(@RequestParameters final Map<String, String[]> params) {
        this.id = extractParameter(params, "id");
    }


// --------------------- GETTER / SETTER METHODS ---------------------

    public String getId() {
        return id;
    }
}