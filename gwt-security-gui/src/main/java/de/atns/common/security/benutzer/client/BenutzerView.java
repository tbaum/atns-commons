package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import de.atns.common.crud.client.PagePresenter;
import de.atns.common.gwt.client.DefaultErrorWidgetDisplay;
import de.atns.common.gwt.client.ExtendedFlexTable;
import de.atns.common.gwt.client.ExtendedFlowPanel;
import de.atns.common.gwt.client.Table;
import de.atns.common.gwt.client.model.StandardFilter;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import org.cobogw.gwt.user.client.ui.Button;

import static com.google.gwt.dom.client.Style.Unit.PX;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;
import static de.atns.common.gwt.client.Table.*;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerView extends DefaultErrorWidgetDisplay implements BenutzerPresenter.Display {
// ------------------------------ FIELDS ------------------------------

    private final Table table = table("benutzer datatable");

    private final Button neu = new Button("Neuer Benutzer");
    private final Button suche = new Button("Suchen");
    private final TextBox text = new TextBox();
    //    private final ListBox status = new ListBox();
    private final Table.Row pagePresenterPanel = row();
    private boolean containsEmptyRow;
    private PagePresenter.Display pagePresenter;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerView() {
        ExtendedFlowPanel extendedFlowPanel = ExtendedFlowPanel.extendedFlowPanel(5)
                .add(getErrorPanel())
                .add(getLoader())
                .newLine()
                .add("Benutzer:").addStyle("heading")
                .newLine()
                .add(neu)
                .newLine()
                .add(flowPanel("benutzer",
                        table("suche filtertable", head(
                                "Suche:",
                                flowPanel(text),
                                suche
                        )),
                        table,
                        pagePresenterPanel
                ));

        final FlowPanel panel = extendedFlowPanel.getPanel();
        panel.getElement().getStyle().setMarginLeft(20, PX);
        initWidget(panel);
        pagePresenterPanel.setWidth("640px");

//      
//        final FlowPanel formPanel = new FlowPanel();
//        formPanel.add(getLoader());
//
//        formPanel.getElement().getStyle().setProperty("textAlign", "left");
//        formPanel.getElement().getStyle().setProperty("padding", "0 0 20px 20px");
//
//
//        formPanel.getElement().getStyle().setPaddingBottom(1, Style.Unit.EM);
//
//
//        formPanel.add(getLoader());
//
//        final Label w1 = new Label("Benutzer - Übersicht:");
//        w1.addStyleName("heading");
//        formPanel.add(w1);
//
////        status.addItem("", "");
////        for (Status auftragsStatus : Status.values()) {
////            status.addItem(auftragsStatus.name());
////        }
//
//
//        final FlexTable table = table()
//                .cell("Text").width(55)
//                .cell(text).width(180)
//                .cell(suche).width(394)
//                .getTable();
//
//        table.addStyleName("filtertable");
//
//        formPanel.add(getErrorPanel());
//        formPanel.add(table);
//
//        auftraege.getTable().addStyleName("datatable");
//        formPanel.add(auftraege.getTable());
//        formPanel.add(pagePresenterPanel);
//        clearList();
//
//        initWidget(extendedFlowPanel.getPanel());
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override public HandlerRegistration forNeu(final ClickHandler clickHandler) {
        return neu.addClickHandler(clickHandler);
    }

    @Override public StandardFilter getFilter() {
        return new StandardFilter(text.getValue());
    }

    public HandlerRegistration addRow(final BenutzerPresentation g, final ClickHandler editHandler) {
        if (containsEmptyRow) {
            clearList();
            setPagination(pagePresenter.asWidget());
        }
        final Button edit = new Button("Bearbeiten");
        table.add(row(
                g.getLogin(),
                g.isAdmin() ? "Admin" : "Nutzer",
                g.getEmail(),
                flowPanel(edit)

        ));

        return edit.addClickHandler(editHandler);
    }

// --------------------- Interface ErrorWidgetDisplay ---------------------

    public void reset() {
        clearList();
        containsEmptyRow = true;
        setPagination(new HTML("- keine Benutzer gefunden -"));
    }

// --------------------- Interface ListWidgetDisplay ---------------------


    @Override public void setPagePresenter(final PagePresenter.Display display) {
        pagePresenter = display;
        setPagination(pagePresenter.asWidget());
    }

    private void setPagination(Widget widget) {
        pagePresenterPanel.clear();
        pagePresenterPanel.add(widget);
    }

    @Override public HandlerRegistration forSuche(final ClickHandler clickHandler) {
        return suche.addClickHandler(clickHandler);
    }

    @Override public HandlerRegistration forPressEnter(final KeyPressHandler handler) {
        return text.addKeyPressHandler(handler);
    }

// -------------------------- OTHER METHODS --------------------------

    private void clearList() {
        containsEmptyRow = false;
        table.clear();
        table.add(head(
                "Login",
                "Rolle",
                "Email",
                ""
        ));
    }
}
