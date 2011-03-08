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
import de.atns.common.gwt.client.DefaultWidgetDisplay;
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
public class BenutzerView extends DefaultWidgetDisplay implements BenutzerPresenter.Display {
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
        final ExtendedFlowPanel extendedFlowPanel = ExtendedFlowPanel.extendedFlowPanel(5)
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

    public void reset() {
        clearList();
        containsEmptyRow = true;
        setPagination(new HTML("- keine Benutzer gefunden -"));
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

    private void setPagination(final Widget widget) {
        pagePresenterPanel.clear();
        pagePresenterPanel.add(widget);
    }
}
