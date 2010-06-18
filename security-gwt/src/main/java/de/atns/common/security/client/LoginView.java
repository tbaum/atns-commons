package de.atns.common.security.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.atns.common.gwt.client.DialogBoxErrorWidgetDisplay;
import de.atns.common.security.client.model.ApplicationName;
import org.cobogw.gwt.user.client.ui.Button;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class LoginView extends DialogBoxErrorWidgetDisplay implements KeyUpHandler, LoginPresenter.Display {
// ------------------------------ FIELDS ------------------------------

    private final Button login;
    private final TextBox username;
    private final PasswordTextBox password;
    private final CheckBox remember;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public LoginView(@ApplicationName final String appName) {
        username = new TextBox();
        username.setWidth("150px");
        username.addKeyUpHandler(this);
        username.setFocus(true);

        password = new PasswordTextBox();
        password.setWidth("150px");
        password.addKeyUpHandler(this);

        remember = new CheckBox("speichern");

        login = new Button("Anmelden");
        login.addStyleName("notloading");
        login.setSize(100);

        final FlexTable grid = new FlexTable();

        grid.getCellFormatter().addStyleName(0, 0, "form-heading");
        grid.getCellFormatter().getElement(0, 0).getStyle().setProperty("paddingBottom", 10, Style.Unit.PX);
        grid.getElement().getStyle().setProperty("paddingBottom", 10, Style.Unit.PX);
        grid.getFlexCellFormatter().setColSpan(0, 0, 2);

        grid.setText(1, 0, "Login");
        grid.getCellFormatter().addStyleName(1, 0, "form-label");

        grid.setWidget(1, 1, username);

        grid.setText(2, 0, "Passwort");
        grid.getCellFormatter().addStyleName(2, 0, "form-label");
        grid.setWidget(2, 1, password);

        grid.setWidget(3, 1, remember);

        grid.getElement().getStyle().setProperty("margin", "auto");

        final FlowPanel flowPanel = new FlowPanel();
        flowPanel.add(grid);
        flowPanel.add(getErrorPanel());
        flowPanel.add(login);

        final Style style = flowPanel.getElement().getStyle();
        style.setProperty("textAlign", "center");
        style.setProperty("paddingBottom", "10px");

        style.setProperty("padding", "0 0 20px 20px");
        style.setBackgroundColor("#ffffff");

        updateLoginButton();

        setDialogBoxContent(appName + ": LOGIN", flowPanel);
    }

    private void updateLoginButton() {
        login.setEnabled(username.getText().length() > 0 && password.getText().length() > 0);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override public void startProcessing() {
        login.addStyleName("loading");
        login.setEnabled(false);
        username.setEnabled(false);
        password.setEnabled(false);
    }

    @Override public void stopProcessing() {
        login.removeStyleName("loading");
        updateLoginButton();
        username.setEnabled(true);
        password.setEnabled(true);
    }

    @Override public HandlerRegistration addLoginClick(final ClickHandler clickHandler) {
        return login.addClickHandler(clickHandler);
    }

    @Override public String passwordValue() {
        return password.getValue();
    }

    @Override public void passwordValue(final String username) {
        this.password.setText(username);
        updateLoginButton();
    }

    @Override public boolean rememberValue() {
        return remember.getValue();
    }

    @Override public void rememberValue(boolean v) {
        remember.setValue(v);
    }

    @Override public String usernameValue() {
        return username.getValue();
    }

    @Override public void usernameValue(final String username) {
        this.username.setText(username);
        updateLoginButton();
    }

// --------------------- Interface KeyUpHandler ---------------------

    @Override public void onKeyUp(final KeyUpEvent event) {
        updateLoginButton();

        if (event.getNativeKeyCode() == KEY_ENTER) {
            if (event.getSource().equals(username)) {
                password.setFocus(true);
            } else if (event.getSource().equals(password)) {
                if (login.isEnabled()) {
                    login.click();
                }
            }
        }
    }

    @Override public void reset() {
        username.setText("");
        password.setText("");
        updateLoginButton();
    }
}
