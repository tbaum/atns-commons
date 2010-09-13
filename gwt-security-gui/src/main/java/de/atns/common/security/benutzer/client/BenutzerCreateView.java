package de.atns.common.security.benutzer.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import de.atns.common.gwt.client.DialogBoxErrorWidgetDisplay;
import de.atns.common.gwt.client.FieldSetPanel;
import org.cobogw.gwt.user.client.ui.Button;

import static de.atns.common.gwt.client.ExtendedFlowPanel.extendedFlowPanel;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerCreateView extends DialogBoxErrorWidgetDisplay implements BenutzerCreatePresenter.Display {
// ------------------------------ FIELDS ------------------------------

    private final Button speichern = new Button("Speichern");

    private final TextBox login = new TextBox();
    private final TextBox email = new TextBox();
    private final PasswordTextBox passwort1 = new PasswordTextBox();
    private final PasswordTextBox passwort2 = new PasswordTextBox();
    private final CheckBox admin = new CheckBox();

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerCreateView() {
        final FlowPanel fp = flowPanel(speichern, getCancelButton());
        fp.getElement().getStyle().setProperty("textAlign", "center");
        fp.getElement().getStyle().setMargin(5, Style.Unit.PX);
        //    setWidth("250px")

        login.addKeyUpHandler(new KeyUpHandler() {
            @Override public void onKeyUp(final KeyUpEvent event) {
                updButton();
            }
        });
        passwort1.addKeyUpHandler(new KeyUpHandler() {
            @Override public void onKeyUp(final KeyUpEvent event) {
                updButton();
            }
        });
        passwort2.addKeyUpHandler(new KeyUpHandler() {
            @Override public void onKeyUp(final KeyUpEvent event) {
                updButton();
            }
        });

        login.addKeyPressHandler(new KeyPressHandler() {
            @Override public void onKeyPress(final KeyPressEvent event) {
                updButton();
            }
        });
        passwort1.addKeyPressHandler(new KeyPressHandler() {
            @Override public void onKeyPress(final KeyPressEvent event) {
                updButton();
            }
        });
        passwort2.addKeyPressHandler(new KeyPressHandler() {
            @Override public void onKeyPress(final KeyPressEvent event) {
                updButton();
            }
        });

        login.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override public void onValueChange(final ValueChangeEvent<String> event) {
                updButton();
            }
        });
        passwort1.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override public void onValueChange(final ValueChangeEvent<String> event) {
                updButton();
            }
        });
        passwort2.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override public void onValueChange(final ValueChangeEvent<String> event) {
                updButton();
            }
        });


        setDialogBoxContent("Benutzer - Anlegen", flowPanel(
                new FieldSetPanel("Benutzer", extendedFlowPanel()
                        .add("Login").widthPX(120).add(login).newLine()
                        .add("Admin").widthPX(120).add(admin).newLine()
                        .add("Email").widthPX(120).add(email).newLine()
                        .add("Passwort").widthPX(120).add(passwort1).newLine()
                        .add("weiderholen").widthPX(120).add(passwort2).newLine()
                        .getPanel()
                ),
                getErrorPanel(),
                fp
        ));
        setGlassEnabled(true);
        updButton();
    }

    private void updButton() {
        final String lg = login.getValue();
        final String p1 = passwort1.getValue();
        final String p2 = passwort2.getValue();
        speichern.setEnabled(lg.length() > 2 && p1.length() > 5 && p1.equals(p2));
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override public void reset() {
        login.setValue("");
        email.setValue("");
        passwort1.setValue("");
        admin.setValue(false);
        passwort2.setValue("");
        updButton();
    }

    @Override public HandlerRegistration addSafeHandler(final ClickHandler handler) {
        return speichern.addClickHandler(handler);
    }

    @Override public String getEmail() {
        return email.getValue();
    }

    @Override public String getPasswort() {
        return passwort1.getValue();
    }

    @Override public boolean isAdmin() {
        return admin.getValue();
    }

    @Override public String getLogin() {
        return login.getValue();
    }
}