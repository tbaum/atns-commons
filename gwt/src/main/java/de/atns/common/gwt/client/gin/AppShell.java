package de.atns.common.gwt.client.gin;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author tbaum
 * @since 22.11.10
 */
public interface AppShell extends IsWidget {
// -------------------------- OTHER METHODS --------------------------

    String getAppName();

    AcceptsOneWidget getContentWidget();
}
