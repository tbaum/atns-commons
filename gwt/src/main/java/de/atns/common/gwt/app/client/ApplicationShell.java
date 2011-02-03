package de.atns.common.gwt.app.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import de.atns.common.gwt.client.DefaultWidgetDisplay;
import de.atns.common.gwt.client.gin.AppShell;
import de.atns.common.gwt.client.window.PopupWindowEventBus;
import de.atns.common.security.client.model.ApplicationName;
import de.atns.common.security.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 05.11.10
 */
public class ApplicationShell extends DefaultWidgetDisplay implements ApplicationPresenter.Display, AppShell {
// ------------------------------ FIELDS ------------------------------

    @UiField SimplePanel contentPanel;
    @UiField SimplePanel navigation;

    private final Navigation loginMenu;
    private final String appName;
    private final NavigationNoAuth logoutMenu;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public ApplicationShell(final NavigationNoAuth logoutMenu, final Navigation loginMenu,
                            @ApplicationName final String appName) {
        this.logoutMenu = logoutMenu;
        this.loginMenu = loginMenu;
        this.appName = appName;

        final ShellUiBinder uiBinder = GWT.create(ShellUiBinder.class);
        initWidget(uiBinder.createAndBindUi(this));

        show(false);
    }

    public void show(final boolean isAuth) {
        if (PopupWindowEventBus.isRunningInPopup()) {
            navigation.setVisible(false);
        } else {
            navigation.setWidget(isAuth ? loginMenu : logoutMenu);
        }
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAppName() {
        return appName;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override
    public void setUser(final UserPresentation user) {
        loginMenu.setUsername(user != null ? user.getLogin() : "");
    }

    public HasOneWidget getPanel() {
        return contentPanel;
    }

// --------------------- Interface WidgetDisplay ---------------------

    @Override public void reset() {
        show(false);
    }

// -------------------------- INNER CLASSES --------------------------

    interface ShellUiBinder extends UiBinder<Widget, ApplicationShell> {
    }
}
