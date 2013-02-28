package de.atns.common.security.benutzer.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;
import de.atns.common.gwt.client.DefaultDialogBoxDisplay;
import de.atns.common.gwt.client.ExtendedFlexTable;
import de.atns.common.gwt.client.FieldSetPanel;
import de.atns.common.security.RoleConverter;
import de.atns.common.security.SecurityRole;
import de.atns.common.security.client.model.SecurityRolePresentation;
import de.atns.common.security.client.model.UserPresentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static de.atns.common.gwt.client.ExtendedFlowPanel.extendedFlowPanel;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerDetailView extends DefaultDialogBoxDisplay implements BenutzerEditPresenter.Display, BenutzerCreatePresenter.Display {

    protected final TextBox login = new TextBox();
    protected final PasswordTextBox passwort1 = new PasswordTextBox();
    protected final TextBox email = new TextBox();
    protected final TextBox name = new TextBox();
    protected final Button speichern = new Button("Speichern");
    protected final PasswordTextBox passwort2 = new PasswordTextBox();
    protected final Map<Class<? extends SecurityRole>, CheckBox> roles = new HashMap<Class<? extends SecurityRole>, CheckBox>();
    private final RoleConverter roleConverter;

    @Inject public BenutzerDetailView(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
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
                        .add("Email").widthPX(120).add(email).newLine()
                        .add("Name").widthPX(120).add(name).newLine()
                        .getPanel()
                ),
                new FieldSetPanel("Rollen", createRolePanel()),
                new FieldSetPanel("Passwort", extendedFlowPanel()
                        .add("Passwort").widthPX(120).add(passwort1).newLine()
                        .add("wiederholen").widthPX(120).add(passwort2).newLine()
                        .getPanel()
                ),
                getErrorPanel(),
                fp
        ));
        setGlassEnabled(true);
        updButton();
    }

    private Widget createRolePanel() {
        roles.clear();
        ExtendedFlexTable panel = ExtendedFlexTable.table();
        final FlexTable table = panel.getTable();
        table.setBorderWidth(0);
        table.setCellPadding(5);
        int i = 0;
        for (String role : roleConverter) {
            CheckBox checkBox = new CheckBox(role);
            panel.cell(checkBox);
            i++;
            if (i % 3 == 0) {
                panel.nextRow();
            }
            roles.put(roleConverter.toRole(role), checkBox);
        }
        return table;
    }

    protected void updButton() {
        final String p1 = passwort1.getValue();
        final String p2 = passwort2.getValue();
        speichern.setEnabled((p1.isEmpty() && p2.isEmpty()) || (p1.length() > 5 && p1.equals(p2)));
    }

    private Set<SecurityRolePresentation> getSelectedRoles() {
        Set<SecurityRolePresentation> selectedRoles = new HashSet<SecurityRolePresentation>();
        for (Map.Entry<Class<? extends SecurityRole>, CheckBox> role : roles.entrySet()) {
            final CheckBox checkBox = role.getValue();
            if (checkBox.getValue()) {
                selectedRoles.add(new SecurityRolePresentation(role.getKey()));
            }
        }
        return selectedRoles;
    }

    @Override public void setData(final UserPresentation p) {
        login.setValue(p.getLogin());
        email.setValue(p.getEmail());
        name.setValue(p.getName());
        passwort1.setValue("");
        passwort2.setValue("");

        for (CheckBox checkBox : roles.values()) {
            checkBox.setValue(false);
        }

        for (SecurityRolePresentation securityRolePresentation : p.getRoles()) {
            roles.get(securityRolePresentation.getRole()).setValue(true);
        }

        updButton();
    }

    @Override public HandlerRegistration forSafe(final ClickHandler handler) {
        return speichern.addClickHandler(handler);
    }

    @Override public UserPresentation getData(UserPresentation p) {
        return new UserPresentation(p.getId(), login.getValue(), name.getValue(), passwort1.getValue(),
                email.getValue(), getSelectedRoles(), null, null);
    }

    @Override public void reset() {
        login.setValue("");
        email.setValue("");
        name.setValue("");
        passwort1.setValue("");
        passwort2.setValue("");
        updButton();
    }

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
