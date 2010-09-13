package de.atns.common.security.benutzer.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import de.atns.common.gwt.client.DefaultErrorWidgetDisplay;
import de.atns.common.gwt.client.ExtendedFlexTable;
import de.atns.common.gwt.client.PagePresenter;
import de.atns.common.gwt.client.model.StandardFilter;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import org.cobogw.gwt.user.client.ui.Button;

import static de.atns.common.gwt.client.ExtendedFlexTable.table;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerView extends DefaultErrorWidgetDisplay implements BenutzerPresenter.Display {
// ------------------------------ FIELDS ------------------------------

    private static final DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");
    private final ExtendedFlexTable auftraege = table();

    private final Button suche = new Button("Suchen");
    private final TextBox text = new TextBox();
    private final ListBox status = new ListBox();

    private final FlowPanel pagePresenterPanel = new FlowPanel();

    private final NumberFormat fm = NumberFormat.getFormat("0.0");

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerView() {
        final FlowPanel formPanel = new FlowPanel();
        formPanel.add(getLoader());

        formPanel.getElement().getStyle().setProperty("textAlign", "left");
        formPanel.getElement().getStyle().setProperty("padding", "0 0 20px 20px");


        formPanel.getElement().getStyle().setPaddingBottom(1, Style.Unit.EM);


        formPanel.add(getLoader());

        final Label w1 = new Label("Benutzer - Übersicht:");
        w1.addStyleName("heading");
        formPanel.add(w1);

//        status.addItem("", "");
//        for (Status auftragsStatus : Status.values()) {
//            status.addItem(auftragsStatus.name());
//        }


        final FlexTable table = table()
                .cell("Text").width(55)
                .cell(text).width(180)
                .cell(suche).width(394)
                .getTable();

        table.addStyleName("filtertable");

        formPanel.add(getErrorPanel());
        formPanel.add(table);

        auftraege.getTable().addStyleName("datatable");
        formPanel.add(auftraege.getTable());
        formPanel.add(pagePresenterPanel);
        clearList();

        initWidget(formPanel);
    }

    public void clearList() {
        pagePresenterPanel.setWidth("640px");
        auftraege.clear();
        auftraege
                .cell("Login").addStyle("header").width(170)
                .cell("Rolle").addStyle("header").width(170)
                .cell("Email").addStyle("header").width(200)
                .cell("").addStyle("header").width(85)
                .nextRow(true);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override public HandlerRegistration forSuche(final ClickHandler clickHandler) {
        return suche.addClickHandler(clickHandler);
    }

    @Override public StandardFilter getFilter() {
        return new StandardFilter(text.getValue());
    }

    @Override public void addEmptyRow() {
        auftraege.cell("- keine Benutzer gefunden -").colspan(12).
                nextRow();
    }

    public HandlerRegistration addRow(final BenutzerPresentation g, final ClickHandler editHandler) {
        Button edit = new Button("Bearbeiten");

        auftraege
                .cell(g.getLogin())
                .cell(g.isAdmin() ? "Admin" : "Nutzer")
                .cell(g.getEmail())
                .cell(flowPanel(edit))
                .nextRow();


        return edit.addClickHandler(editHandler);
    }

    @Override public void showPagePresenter(final PagePresenter.Display presenter) {
        pagePresenterPanel.add(presenter.asWidget());
    }

    @Override public HandlerRegistration[] forPressEnter(final KeyPressHandler handler) {
        return new HandlerRegistration[]{
                text.addKeyPressHandler(handler),
                status.addKeyPressHandler(handler),
        };
    }

// --------------------- Interface ErrorWidgetDisplay ---------------------

    public void reset() {
        text.setValue("");
        status.setSelectedIndex(0);
    }
}
