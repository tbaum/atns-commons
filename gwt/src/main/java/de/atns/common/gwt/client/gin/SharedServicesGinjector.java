package de.atns.common.gwt.client.gin;

import com.google.gwt.inject.client.Ginjector;

/**
 * @author tbaum
 * @since 04.02.11
 */
public interface SharedServicesGinjector extends Ginjector {
// -------------------------- OTHER METHODS --------------------------

    SharedServicesAware sharedServicesAware();
}
