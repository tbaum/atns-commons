package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import de.atns.common.gwt.client.DefaultErrorWidgetDisplay;
import de.atns.common.gwt.client.ExtendedFlexTable;
import de.atns.common.gwt.client.ExtendedFlowPanel;
import de.atns.common.gwt.client.PagePresenter;
import de.atns.common.gwt.client.model.StandardFilter;
import de.atns.common.security.benutzer.client.action.BenutzerList;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import org.cobogw.gwt.user.client.ui.Button;

import static com.google.gwt.dom.client.Style.Unit.PX;
import static de.atns.common.gwt.client.ExtendedFlexTable.table;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerView extends DefaultErrorWidgetDisplay implements BenutzerPresenter.Display {
// ------------------------------ FIELDS ------------------------------

    private final ExtendedFlexTable table = table("datatable");

    private final Button neu = new Button("Neuer Benutzer");
    private final Button suche = new Button("Suchen");
    private final TextBox text = new TextBox();
//    private final ListBox status = new ListBox();
    private final FlowPanel pagePresenterPanel = new FlowPanel();
    private boolean containsEmptyRow;
    private PagePresenter pagePresenter;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerView() {
        ExtendedFlowPanel extendedFlowPanel = ExtendedFlowPanel.extendedFlowPanel(5)
                .add(getErrorPanel()).newLine()
                .add(getLoader())
                .add("Benutzer:").addStyle("heading").newLine()
                .add(neu).newLine()
                .add(flowPanel(ExtendedFlexTable.table("filtertable")
                        .cell("Suche:")
                        .cell(flowPanel(text)).width(160)
                        .cell(suche).width(76).getTable(), table.getTable(), pagePresenterPanel));

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

    @Override public HandlerRegistration forSuche(final ClickHandler clickHandler) {
        return suche.addClickHandler(clickHandler);
    }

    @Override public StandardFilter getFilter() {
        return new StandardFilter(text.getValue());
    }

    public HandlerRegistration addRow(final BenutzerPresentation g, final ClickHandler editHandler) {
        if (containsEmptyRow) clearList();
        final Button edit = new Button("Bearbeiten");
        table
                .cell(g.getLogin())
                .cell(g.isAdmin() ? "Admin" : "Nutzer")
                .cell(g.getEmail())
                .cell(flowPanel(edit))
                .nextRow();


        return edit.addClickHandler(editHandler);
    }

    @Override public void setPagePresenter(final PagePresenter pagePresenter) {
        this.pagePresenter = pagePresenter;
        pagePresenterPanel.clear();
        pagePresenterPanel.add(pagePresenter.getDisplay().asWidget());
    }

    @Override public HandlerRegistration[] forPressEnter(final KeyPressHandler handler) {
        return new HandlerRegistration[]{
                text.addKeyPressHandler(handler),
        };
    }

    public BenutzerList getData() {
        return new BenutzerList(getFilter(), pagePresenter.getStartEntry(), pagePresenter.getPageRange());
    }

// --------------------- Interface ErrorWidgetDisplay ---------------------


    public void reset() {
        clearList();
        containsEmptyRow = true;
        table.cell("- keine Benutzer gefunden -").colspan(12).
                nextRow();
    }

// -------------------------- OTHER METHODS --------------------------

    private void clearList() {
        containsEmptyRow = false;
        table
                .clear()
                .cell("Login").addStyle("header").width(170)
                .cell("Rolle").addStyle("header").width(170)
                .cell("Email").addStyle("header").width(200)
                .cell("").addStyle("header").width(85)
                .nextRow(true);
    }
}
