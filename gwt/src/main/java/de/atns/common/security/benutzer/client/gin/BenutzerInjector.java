package de.atns.common.security.benutzer.client.gin;

import com.google.gwt.inject.client.GinModules;
import de.atns.common.gwt.client.gin.WidgetPresenterGinjector;
import de.atns.common.security.benutzer.client.BenutzerPresenter;

/**
 * @author tbaum
 * @since 16.06.2010
 */
@GinModules(BenutzerModule.class)
public interface BenutzerInjector extends WidgetPresenterGinjector<BenutzerPresenter> {
}
