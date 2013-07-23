package de.atns.common.gwt.app.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import de.atns.common.gwt.client.DefaultWidgetDisplay;
import de.atns.common.gwt.client.Loader;
import de.atns.common.gwt.client.gin.AppShell;
import de.atns.common.gwt.client.window.PopupWindowEventBus;
import de.atns.common.security.client.model.ApplicationName;
import de.atns.common.security.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 05.11.10
 */
public class ApplicationShell extends DefaultWidgetDisplay implements ApplicationPresenter.Display, AppShell {

    private static final ShellUiBinder UI_BINDER = GWT.create(ShellUiBinder.class);

    @UiField SimplePanel contentPanel;
    @UiField SimplePanel navigation;
    @UiField(provided = true) Loader loader;

    private final Navigation loginMenu;
    private final String appName;
    private final NavigationNoAuth logoutMenu;


    @Inject public ApplicationShell(final NavigationNoAuth logoutMenu, final Navigation loginMenu,
                                    @ApplicationName final String appName, final Loader sharedServices) {
        this.logoutMenu = logoutMenu;
        this.loginMenu = loginMenu;
        this.appName = appName;

        this.loader = sharedServices;

        initWidget(UI_BINDER.createAndBindUi(this));

        show(false);
    }

    @Override public void show(final boolean isAuth) {
        if (PopupWindowEventBus.isRunningInPopup()) {
            navigation.setVisible(false);
        } else {
            navigation.setWidget(isAuth ? loginMenu : logoutMenu);
        }
    }

    @Override public String getAppName() {
        return appName;
    }

    @Override public AcceptsOneWidget getContentWidget() {
        return contentPanel;
    }

    @Override public void setUser(final UserPresentation user) {
        loginMenu.setUsername(user != null ? user.getLogin() : "");
    }

    @Override public void reset() {
        show(false);
    }

    interface ShellUiBinder extends UiBinder<Widget, ApplicationShell> {
    }
}
