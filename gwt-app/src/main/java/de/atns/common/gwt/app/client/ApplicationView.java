package de.atns.common.gwt.app.client;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import de.atns.common.gwt.client.DefaultWidgetDisplay;
import de.atns.common.security.client.event.ServerStatusEventHandler;
import de.atns.common.security.client.model.UserPresentation;

import static de.atns.common.gwt.client.GwtUtil.flowPanel;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class ApplicationView extends DefaultWidgetDisplay implements ApplicationPresenter.Display {
// ------------------------------ FIELDS ------------------------------

    private final NavigationNoAuth logoutMenu;
    private final Navigation loginMenu;

    private final SimplePanel contentPanel = new SimplePanel();
    private final SimplePanel navigation = new SimplePanel();

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public ApplicationView(NavigationNoAuth n1, Navigation n2) {
        logoutMenu = n1;
        loginMenu = n2;
        show(false);
        contentPanel.setWidget(new HTML("START"));

        initWidget(flowPanel(navigation, contentPanel));
    }

    public void show(boolean isAuth) {
        final Widget newNavi = isAuth ? loginMenu : logoutMenu;
        navigation.setWidget(newNavi);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override
    public void setServerStatus(final ServerStatusEventHandler.ServerStatus serverStatus) {
    }

    @Override
    public void setUser(final UserPresentation user) {
        loginMenu.setUsername(user != null ? user.getLogin() : "");
    }

    @Override public AcceptsOneWidget getPanel() {
        return contentPanel;
    }

// --------------------- Interface WidgetDisplay ---------------------

    @Override
    public void reset() {
    }
}
