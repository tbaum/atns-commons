package de.atns.common.gwt.client;

/**
 * @author tbaum
 * @since 09.09.2010
 */
public interface Callback<T> {
    void callback(T result);
}