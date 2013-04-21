package de.atns.common.security.benutzer.client.action;

import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.gwt.client.model.StandardFilter;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.shared.Action;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerList implements Action<ListPresentation<UserPresentation>> {

    private StandardFilter filter;
    private int startEntry;
    private int pageRange;

    protected BenutzerList() {
    }

    public BenutzerList(final StandardFilter filter, final int startEntry, final int pageRange) {
        this.filter = filter;
        this.startEntry = startEntry;
        this.pageRange = pageRange;
    }

    public StandardFilter getFilter() {
        return filter;
    }

    public int getPageRange() {
        return pageRange;
    }

    public int getStartEntry() {
        return startEntry;
    }
}
