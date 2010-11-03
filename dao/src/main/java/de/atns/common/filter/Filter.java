package de.atns.common.filter;


public interface Filter<TYPE> {
// -------------------------- OTHER METHODS --------------------------

    boolean isInFilter(TYPE o);
}
