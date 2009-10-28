package de.atns.shop.tray.data;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.RequestScoped;
import static de.atns.shop.tray.Util.extractParameter;

import java.util.Map;

@RequestScoped public class Data {
// ------------------------------ FIELDS ------------------------------

    private final String data;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public Data(@RequestParameters final Map<String, String[]> params) {
        this.data = extractParameter(params, "data");
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getData() {
        return data;
    }
}