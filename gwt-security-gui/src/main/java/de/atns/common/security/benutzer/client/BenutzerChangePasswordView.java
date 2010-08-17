package de.atns.common.security.benutzer.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import de.atns.common.gwt.client.DialogBoxErrorWidgetDisplay;
import de.atns.common.gwt.client.FieldSetPanel;
import org.cobogw.gwt.user.client.ui.Button;

import static de.atns.common.gwt.client.ExtendedFlowPanel.extendedFlowPanel;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerChangePasswordView extends DialogBoxErrorWidgetDisplay implements BenutzerChangePasswordPresenter.Display {
// ------------------------------ FIELDS ------------------------------

    private final Button speichern = new Button("Speichern");

    private final PasswordTextBox passwort1 = new PasswordTextBox();
    private final PasswordTextBox passwort2 = new PasswordTextBox();

// --------------------------- CONSTRUCTORS ---------------------------

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
                        .getPanel()
                ), getErrorPanel(),
                fp
        ));
        setGlassEnabled(true);
        updButton();
    }

    private void updButton() {
        speichern.setEnabled(passwort1.getValue().length() > 5 && passwort1.getValue().equals(passwort2.getValue()));
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DialogBoxDisplayInterface ---------------------

    @Override public void showDialogBox() {
        super.showDialogBox();
        passwort1.setFocus(true);
    }

// --------------------- Interface Display ---------------------

    @Override public void reset() {
        passwort1.setValue("");
        passwort2.setValue("");
        updButton();
    }

    @Override public HandlerRegistration addSafeHandler(final ClickHandler handler) {
        return speichern.addClickHandler(handler);
    }

    @Override public String getPassword() {
        return passwort1.getValue();
    }
}