package de.atns.common.filter;

public interface Filter<TYPE> {

    boolean isInFilter(TYPE o);
}
