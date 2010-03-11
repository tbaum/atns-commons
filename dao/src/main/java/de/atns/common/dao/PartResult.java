package de.atns.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PartResult<TYPE extends Serializable> implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -1948780339175966559L;
    private int start;
    private final int total;
    private final List<TYPE> items;

// -------------------------- STATIC METHODS --------------------------

    public static <T extends Serializable> PartResult<T> createPartResult(PartResult<T> p, final Collection<T> result) {
        return new PartResult<T>(p.start, p.total, result);
    }

    public static <T extends Serializable> PartResult<T> createPartResult(final int start, final int total, final Collection<T> result) {
        return new PartResult<T>(start, total, result);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private PartResult(final int start, final int total, final Collection<TYPE> elements) {
        this.start = start;
        this.total = total;
        this.items = Collections.unmodifiableList(new ArrayList<TYPE>(elements));
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public List<TYPE> getItems() {
        return items;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }
}
