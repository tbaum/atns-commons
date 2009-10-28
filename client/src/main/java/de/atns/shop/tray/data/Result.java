package de.atns.shop.tray.data;

import com.google.inject.servlet.RequestScoped;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RequestScoped public class Result {
// ------------------------------ FIELDS ------------------------------

    private final Map<String, Serializable> result = new LinkedHashMap<String, Serializable>();

// -------------------------- OTHER METHODS --------------------------

    public Map<String, Serializable> map() {
        return Collections.unmodifiableMap(result);
    }

    public void put(final String key, final Serializable value) {
        result.put(key, value);
    }
}