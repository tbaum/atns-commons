package de.atns.common.gwt.app.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
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

    private static final ShellUiBinder UI_BINDER = GWT.create(ShellUiBinder.class);

    @UiField SimplePanel contentPanel;
    @UiField SimplePanel navigation;

    private final Navigation loginMenu;
    private final String appName;
    private final NavigationNoAuth logoutMenu;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public ApplicationShell(final NavigationNoAuth logoutMenu, final Navigation loginMenu,
                                    @ApplicationName final String appName) {
        this.logoutMenu = logoutMenu;
        this.loginMenu = loginMenu;
        this.appName = appName;

        initWidget(UI_BINDER.createAndBindUi(this));

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

    @Override public AcceptsOneWidget getContentWidget() {
        return contentPanel;
    }

    @Override public void setUser(final UserPresentation user) {
        loginMenu.setUsername(user != null ? user.getLogin() : "");
    }

// --------------------- Interface WidgetDisplay ---------------------

    @Override public void reset() {
        show(false);
    }

// -------------------------- INNER CLASSES --------------------------

    interface ShellUiBinder extends UiBinder<Widget, ApplicationShell> {
    }
}
