package de.atns.common.security.benutzer.client.action;

import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.security.benutzer.client.model.MitarbeiterFilter;
import de.atns.common.security.benutzer.client.model.MitarbeiterPresentation;
import net.customware.gwt.dispatch.shared.Action;


/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerList implements Action<ListPresentation<MitarbeiterPresentation>> {
// ------------------------------ FIELDS ------------------------------

    private MitarbeiterFilter filter;
    private int startEntry;
    private int pageRange;

// --------------------------- CONSTRUCTORS ---------------------------

    protected BenutzerList() {
    }

    public BenutzerList(final MitarbeiterFilter filter, final int startEntry, final int pageRange) {
        this.filter = filter;
        this.startEntry = startEntry;
        this.pageRange = pageRange;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public MitarbeiterFilter getFilter() {
        return filter;
    }

    public int getPageRange() {
        return pageRange;
    }

    public int getStartEntry() {
        return startEntry;
    }
}