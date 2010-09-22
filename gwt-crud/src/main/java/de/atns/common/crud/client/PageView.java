package de.atns.common.crud.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.inject.Inject;
import de.atns.common.gwt.client.DefaultErrorWidgetDisplay;
import de.atns.common.gwt.client.ExtendedFlowPanel;

import static com.google.gwt.dom.client.Style.Unit.PX;
import static de.atns.common.gwt.client.ExtendedFlowPanel.extendedFlowPanel;
import static de.atns.common.gwt.client.GwtUtil.createLabel;
import static de.atns.common.gwt.client.GwtUtil.flowPanel;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class PageView extends DefaultErrorWidgetDisplay implements PagePresenter.Display {
// ------------------------------ FIELDS ------------------------------

    private final ListBox rangeBox = new ListBox();
    private final ExtendedFlowPanel leftPanel = extendedFlowPanel();

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public PageView() {
        leftPanel.setPadding(5);

        final Label items = createLabel("Anzahl:");
        items.getElement().getStyle().setMarginRight(10, PX);
        final ExtendedFlowPanel rightPanel = extendedFlowPanel(items, rangeBox);

        final FlowPanel leftP = leftPanel.getPanel();
        leftP.getElement().getStyle().setDisplay(Style.Display.INLINE);
        final FlowPanel rightP = rightPanel.getPanel();

        rightP.getElement().getStyle().setProperty("cssFloat", "right");

        initWidget(flowPanel(createLabel("Seite", true), leftP, rightP));
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Display ---------------------

    @Override public void reset() {
        leftPanel.clear();
    }

    @Override
    public HandlerRegistration addSeitenButton(final int site, final ClickHandler clickHandler, final boolean active) {
        Label label = new Label("" + site);
        final Style style = label.getElement().getStyle();
        style.setCursor(Style.Cursor.POINTER);
        style.setTextDecoration(Style.TextDecoration.UNDERLINE);
        style.setColor("darkblue");

        if (active) {
            style.setFontWeight(Style.FontWeight.BOLD);
        }

        leftPanel.add(label);
        return label.addClickHandler(clickHandler);
    }

    @Override public void addDots() {
        leftPanel.add("...");
    }

    @Override
    public HandlerRegistration addLengthButton(final ChangeHandler handler, final int active, final int... range) {
        for (int i : range) {
            rangeBox.addItem(String.valueOf(i));

            if (active == i) {
                rangeBox.setSelectedIndex(rangeBox.getItemCount() - 1);
            }
        }

        return rangeBox.addChangeHandler(handler);
    }

    @Override public int selectedRange() {
        final int selectedIndex = rangeBox.getSelectedIndex();
        return Integer.parseInt(rangeBox.getValue(selectedIndex));
    }
}