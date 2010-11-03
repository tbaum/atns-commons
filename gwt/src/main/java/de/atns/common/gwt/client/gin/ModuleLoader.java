package de.atns.common.gwt.client.gin;

import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.gwt.client.WidgetPresenter;

public interface ModuleLoader {
// -------------------------- OTHER METHODS --------------------------

    void load(final Callback callback);

// -------------------------- INNER CLASSES --------------------------

    interface Callback {
        void onLoaded(WidgetPresenter<? extends WidgetDisplay> presenter);
    }
}

