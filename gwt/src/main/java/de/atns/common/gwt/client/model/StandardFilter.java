package de.atns.common.gwt.client.model;

import java.io.Serializable;


/**
 * @author mwolter
 * @since 01.03.2010 12:07:01
 */
public class StandardFilter implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private String filterText;

// --------------------------- CONSTRUCTORS ---------------------------

    protected StandardFilter() {
    }

    public StandardFilter(final String filterText) {
        this.filterText = filterText.equals("") ? null : "%" + filterText + "%";
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getFilterText() {
        return filterText;
    }
}
