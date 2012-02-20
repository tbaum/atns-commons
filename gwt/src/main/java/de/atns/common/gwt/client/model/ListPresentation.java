package de.atns.common.gwt.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.dispatch.shared.Result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author tbaum
 * @since 12.02.2010
 */
public class ListPresentation<E extends IsSerializable> implements Result {
// ------------------------------ FIELDS ------------------------------

    private ArrayList<E> list;
    private boolean moreResults;
    private int start;
    private int total;

// --------------------------- CONSTRUCTORS ---------------------------

    public ListPresentation() {
    }

    public ListPresentation(final Collection<E> list, final boolean moreResults) {
        this.list = new ArrayList<E>(list);
        this.moreResults = moreResults;
    }

    public ListPresentation(final Collection<E> list, final int start, final int total) {
        this.list = new ArrayList<E>(list);
        this.start = start;
        this.total = total;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public boolean isMoreResults() {
        return moreResults;
    }

// -------------------------- OTHER METHODS --------------------------

    public ArrayList<E> getEntries() {
        return list;
    }
}
