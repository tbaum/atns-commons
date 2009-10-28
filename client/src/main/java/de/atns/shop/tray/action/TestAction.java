package de.atns.shop.tray.action;

import com.google.inject.servlet.RequestScoped;
import de.atns.shop.tray.Action;

@RequestScoped public class TestAction implements Action {
// ------------------------------ FIELDS ------------------------------


// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

    @Override public boolean localOnly() {
        return false;
    }

    @Override public void service() {

    }
}