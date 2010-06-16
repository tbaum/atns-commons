package de.atns.common.gwt.client.gin;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

/**
 * @author tbaum
 * @since 14.06.2010
 */
public interface ModuleLoaderCallback {
// -------------------------- OTHER METHODS --------------------------

    void onLoaded(WidgetPresenter<? extends WidgetDisplay> presenter);
}
