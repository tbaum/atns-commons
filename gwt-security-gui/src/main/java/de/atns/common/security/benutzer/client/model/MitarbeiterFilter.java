package de.atns.common.security.benutzer.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author tbaum
 * @since 11.02.2010
 */
public class MitarbeiterFilter implements IsSerializable {
// ------------------------------ FIELDS ------------------------------

    private String text;

// --------------------------- CONSTRUCTORS ---------------------------

    protected MitarbeiterFilter() {
    }

    public MitarbeiterFilter(final String text) {
        this.text = text;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getText() {
        return text;
    }
}