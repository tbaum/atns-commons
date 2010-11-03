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
import de.atns.common.gwt.client.DefaultDialogBoxDisplay;
import de.atns.common.gwt.client.FieldSetPanel;
import org.cobogw.gwt.user.client.ui.Button;

import static de.atns.common.gwt.client.ExtendedFlowPanel.extendedFlowPanel;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;


/**
 * @author tbaum
 * @since 24.10.2009
 */
public abstract class BenutzerDetailView extends DefaultDialogBoxDisplay {
// ------------------------------ FIELDS ------------------------------

    protected final TextBox login = new TextBox();
    protected final TextBox email = new TextBox();
    protected final PasswordTextBox passwort1 = new PasswordTextBox();
    protected final CheckBox admin = new CheckBox();
    protected final Button speichern = new Button("Speichern");
    protected final PasswordTextBox passwort2 = new PasswordTextBox();

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerDetailView() {
        final FlowPanel fp = flowPanel(speichern, getCancelButton());
        fp.getElement().getStyle().setProperty("textAlign", "center");
        fp.getElement().getStyle().setMargin(5, Style.Unit.PX);
        //    setWidth("250px")
        final Handler h = new Handler();
        login.addKeyUpHandler(h);
        passwort1.addKeyUpHandler(h);
        passwort2.addKeyUpHandler(h);

        login.addKeyPressHandler(h);
        passwort1.addKeyPressHandler(h);
        passwort2.addKeyPressHandler(h);

        login.addValueChangeHandler(h);
        passwort1.addValueChangeHandler(h);
        passwort2.addValueChangeHandler(h);


        setDialogBoxContent("Benutzer - Anlegen/Bearbeiten", flowPanel(
                new FieldSetPanel("Benutzer", extendedFlowPanel()
                        .add("Login").widthPX(120).add(login).newLine()
                        .add("Admin").widthPX(120).add(admin).newLine()
                        .add("Email").widthPX(120).add(email).newLine()
                        .getPanel()
                ), new FieldSetPanel("Passwort", extendedFlowPanel()
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

    protected abstract void updButton();

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ErrorWidgetDisplay ---------------------

    @Override public void reset() {
        login.setValue("");
        email.setValue("");
        admin.setValue(false);
        passwort1.setValue("");
        passwort2.setValue("");
        updButton();
    }

// -------------------------- OTHER METHODS --------------------------

    public HandlerRegistration forSafe(final ClickHandler handler) {
        return speichern.addClickHandler(handler);
    }

// -------------------------- INNER CLASSES --------------------------

    private class Handler implements ValueChangeHandler<String>, KeyPressHandler, KeyUpHandler {
        @Override public void onKeyPress(final KeyPressEvent event) {
            updButton();
        }

        @Override public void onKeyUp(final KeyUpEvent event) {
            updButton();
        }

        @Override public void onValueChange(final ValueChangeEvent<String> stringValueChangeEvent) {
            updButton();
        }
    }
}
