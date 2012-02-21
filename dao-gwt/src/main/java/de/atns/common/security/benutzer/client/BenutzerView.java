package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;
import de.atns.common.crud.client.PagePresenter;
import de.atns.common.gwt.client.DefaultWidgetDisplay;
import de.atns.common.gwt.client.Table;
import de.atns.common.gwt.client.model.StandardFilter;
import de.atns.common.security.RoleConverter;
import de.atns.common.security.SecurityRolePresentation;
import de.atns.common.security.client.model.UserPresentation;

import static com.google.gwt.dom.client.Style.Unit.PX;
import static de.atns.common.gwt.client.ExtendedFlowPanel.extendedFlowPanel;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;
import static de.atns.common.gwt.client.Table.*;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerView extends DefaultWidgetDisplay implements BenutzerPresenter.Display {
    public static final DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getFormat("dd.MM.yyyy hh:mm");
    // ------------------------------ FIELDS ------------------------------

    private final Table table = table("benutzer datatable");

    private final Button neu = new Button("Neuer Benutzer");
    private final Button suche = new Button("Suchen");
    private final TextBox text = new TextBox();
    //    private final ListBox status = new ListBox();
    private final Row pagePresenterPanel = row();
    private boolean containsEmptyRow;
    private PagePresenter.Display pagePresenter;

    private final RoleConverter roleConverter;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerView(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;

        final FlowPanel panel = extendedFlowPanel(5)
                .add(getErrorPanel()).add(getLoader()).newLine()
                .add("Benutzer:").addStyle("heading").newLine()
                .add(neu).newLine()
                .add(flowPanel("benutzer",
                        table("suche filtertable", head("Suche:", flowPanel(text), suche)),
                        table,
                        pagePresenterPanel
                )).getPanel();
        panel.getElement().getStyle().setMarginLeft(20, PX);
        initWidget(panel);
        pagePresenterPanel.setWidth("640px");
    }


// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override public HandlerRegistration forNeu(final ClickHandler clickHandler) {
        return neu.addClickHandler(clickHandler);
    }

    @Override public StandardFilter getFilter() {
        return new StandardFilter(text.getValue());
    }

    @Override public HandlerRegistration addRow(final UserPresentation presentation, final ClickHandler editHandler) {
        if (containsEmptyRow) {
            clearList();
            setPagination(pagePresenter.asWidget());
        }
        final Button edit = new Button("Bearbeiten");

        table.add(row(presentation.getLogin(), getRolesAsString(presentation), presentation.getName(),
                presentation.getEmail(),
                presentation.getLastLogin() != null ? DATE_TIME_FORMAT.format(presentation.getLastLogin()) : "",
                presentation.getLastAccess() != null ? DATE_TIME_FORMAT.format(presentation.getLastAccess()) : "",
                flowPanel(edit)));

        return edit.addClickHandler(editHandler);
    }

// --------------------- Interface ListDisplay ---------------------

    @Override public void setPagePresenter(final PagePresenter.Display display) {
        pagePresenter = display;
        setPagination(pagePresenter.asWidget());
    }

    @Override public HandlerRegistration forSuche(final ClickHandler clickHandler) {
        return suche.addClickHandler(clickHandler);
    }

    @Override public HandlerRegistration forPressEnter(final KeyPressHandler handler) {
        return text.addKeyPressHandler(handler);
    }

// --------------------- Interface WidgetDisplay ---------------------

    @Override public void reset() {
        clearList();
        containsEmptyRow = true;
        setPagination(new HTML("- keine Benutzer gefunden -"));
    }

// -------------------------- OTHER METHODS --------------------------

    private void clearList() {
        containsEmptyRow = false;
        table.clear();
        table.add(head("Login", "Rollen", "Name", "Email", "letzter Login", "letzte Aktivität", ""));
    }

    private String getRolesAsString(UserPresentation userPresentation) {
        String roles = "";
        for (SecurityRolePresentation presentation : userPresentation.getRoles()) {
            roles += (roles.isEmpty() ? "" : ", ") + roleConverter.toString(presentation.getRole());
        }
        return roles;
    }

    private void setPagination(final Widget widget) {
        pagePresenterPanel.clear();
        pagePresenterPanel.add(widget);
    }
}
