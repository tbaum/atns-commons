package de.atns.shop.tray.data;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.RequestScoped;

import java.util.Map;

@RequestScoped public class Save {
// ------------------------------ FIELDS ------------------------------

    private final boolean save;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public Save(@RequestParameters final Map<String, String[]> params) {
        this.save = params.get("save") != null;
    }

// -------------------------- OTHER METHODS --------------------------

    public boolean isTrue() {
        return save;
    }
}