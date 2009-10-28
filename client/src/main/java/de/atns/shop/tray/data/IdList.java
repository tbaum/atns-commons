package de.atns.shop.tray.data;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.RequestScoped;

import java.util.Iterator;
import java.util.Map;

@RequestScoped public class IdList implements Iterable<String> {
// ------------------------------ FIELDS ------------------------------

    private final String[] ids;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public IdList(@RequestParameters final Map<String, String[]> params) {
        this.ids = params.get("id");
        if (ids == null || ids.length < 1)
            throw new IllegalArgumentException("Parameter 'id' wrong or missing");
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Iterable ---------------------

    @Override public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int pos = 0;

            @Override public boolean hasNext() {
                return pos < ids.length;
            }

            @Override public String next() {
                return ids[pos++];
            }

            @Override public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}