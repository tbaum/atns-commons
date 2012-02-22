package de.atns.common.gwt.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * @author mwolter
 * @since 01.03.2010 12:07:01
 */
public class StandardFilter implements IsSerializable {

    private String filterText;

    protected StandardFilter() {
    }

    public StandardFilter(final String filterText) {
        this.filterText = filterText.equals("") ? null : filterText;
    }

    public String getFilterText() {
        return filterText != null ? "%" + filterText + "%" : null;
    }

    public String getFilterTextPlain() {
        return filterText;
    }
}
