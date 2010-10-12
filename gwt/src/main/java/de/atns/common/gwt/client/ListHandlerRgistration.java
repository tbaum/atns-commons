package de.atns.common.gwt.client;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author tbaum
 * @since 06.10.2010
 */
public class ListHandlerRgistration implements HandlerRegistration {
// ------------------------------ FIELDS ------------------------------

    private final HandlerRegistration[] handlerRegistration;

// -------------------------- STATIC METHODS --------------------------

    public static HandlerRegistration toList(HandlerRegistration... handlerRegistration) {
        return new ListHandlerRgistration(handlerRegistration);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private ListHandlerRgistration(HandlerRegistration... handlerRegistration) {
        this.handlerRegistration = handlerRegistration;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HandlerRegistration ---------------------

    @Override public void removeHandler() {
        for (HandlerRegistration registration : handlerRegistration) {
            try {
                registration.removeHandler();
            } catch (Exception ignored) {
            }
        }
    }
}