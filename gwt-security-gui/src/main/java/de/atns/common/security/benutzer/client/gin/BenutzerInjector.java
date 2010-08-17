package de.atns.common.security.benutzer.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import de.atns.common.gwt.client.gin.SharedServicesAware;
import de.atns.common.security.benutzer.client.BenutzerPresenter;

/**
 * @author tbaum
 * @since 16.06.2010
 */
@GinModules(BenutzerModule.class)
public interface BenutzerInjector extends Ginjector {
// -------------------------- OTHER METHODS --------------------------

    BenutzerPresenter presenter();

    SharedServicesAware sharedServicesAware();
}
