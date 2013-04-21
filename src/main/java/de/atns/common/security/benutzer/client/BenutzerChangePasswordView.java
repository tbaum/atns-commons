package de.atns.common.security.benutzer.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.web.bindery.event.shared.HandlerRegistration;
import de.atns.common.gwt.client.DefaultDialogBoxDisplay;
import de.atns.common.gwt.client.FieldSetPanel;

import static de.atns.common.gwt.client.ExtendedFlowPanel.extendedFlowPanel;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerChangePasswordView extends DefaultDialogBoxDisplay implements BenutzerChangePasswordPresenter.Display {

    private final Button speichern = new Button("Speichern");
    private final PasswordTextBox passwort1 = new PasswordTextBox();
    private final PasswordTextBox passwort2 = new PasswordTextBox();

    public BenutzerChangePasswordView() {
        final FlowPanel fp = flowPanel(speichern, getCancelButton());
        fp.getElement().getStyle().setProperty("textAlign", "center");
        fp.getElement().getStyle().setMargin(5, Style.Unit.PX);

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

        setDialogBoxContent("Login bearbeiten", flowPanel(
                new FieldSetPanel("Passwort ändern:", extendedFlowPanel()
                        .add("Passwort").widthPX(120).add(passwort1).newLine()
                        .add("weiderholen").widthPX(120).add(passwort2).newLine()
                        .add(getErrorPanel())
                        .getPanel()
                ), getErrorPanel(),
                fp
        ));
        setGlassEnabled(true);
        updButton();
    }

    private void updButton() {
        final boolean passwordLenght = passwort1.getValue().length() > 5;
        final boolean passwordEquals = passwort1.getValue().equals(passwort2.getValue());

        if (!passwordLenght) {
            showError("Paswortlänge mindestens 6 Zeichen!");
        } else if (!passwordEquals) {
            showError("Passwörter stimmen nicht überein!");
        } else {
            resetErrors();
        }

        speichern.setEnabled(passwordLenght && passwordEquals);
    }

    @Override public void showDialogBox() {
        super.showDialogBox();
        passwort1.setFocus(true);
    }

    @Override public HandlerRegistration addSafeHandler(final ClickHandler handler) {
        return speichern.addClickHandler(handler);
    }

    @Override public String getPassword() {
        return passwort1.getValue();
    }

    @Override public void reset() {
        passwort1.setValue("");
        passwort2.setValue("");
        updButton();
    }
}
