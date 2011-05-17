package de.atns.common.gwt.client;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FieldSetPanel extends ComplexPanel implements InsertPanel {
// ------------------------------ FIELDS ------------------------------

    private FieldSetPanel.LegendPanel legendPanel;

// --------------------------- CONSTRUCTORS ---------------------------

    public FieldSetPanel(final Widget legend) {
        this(legend, null);
    }

    @UiConstructor
    public FieldSetPanel(final String text) {
        this(new Label(text));
    }

    public FieldSetPanel(final String text, final Widget content) {
        this(new Label(text), content);
    }

    public FieldSetPanel(final String text, final ExtendedFlowPanel content) {
        this(text, content.getPanel());
    }

    public FieldSetPanel(final Widget legend, final Widget content) {
        legendPanel = new LegendPanel();
        setElement(DOM.createFieldSet());
        legendPanel.add(legend);
        add(legendPanel);
        if (content != null) {
            add(content);
        }
    }

    @Override
    public void add(final Widget w) {
        add(w, getElement());
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface InsertPanel ---------------------

    @Override public void insert(final Widget w, final int beforeIndex) {
        insert(w, getElement(), beforeIndex, true);
    }

// -------------------------- OTHER METHODS --------------------------

    public void addLegend(final Widget legend) {
        legendPanel.add(legend);
    }

    public void addLegend(final String text) {
        addLegend(new Label(text));
    }

    public void clearLegend() {
        for (int i = 0; i < legendPanel.getWidgetCount(); i++) {
            legendPanel.getWidget(i).removeFromParent();
            legendPanel.remove(i);
        }
    }

    public void clearPanel() {
        for (int i = 0; i < getWidgetCount(); i++) {
            getWidget(i).removeFromParent();
            remove(i);
        }
    }

// -------------------------- INNER CLASSES --------------------------

    private class LegendPanel extends ComplexPanel implements InsertPanel {
        public LegendPanel() {
            setElement(DOM.createLegend());
        }

        @Override
        public void add(final Widget w) {
            add(w, getElement());
        }

        @Override
        public void clear() {
            for (int i = 0; i < getWidgetCount(); i++) {
                getWidget(i).removeFromParent();
                remove(i);
            }
        }

        @Override public void insert(final Widget w, final int beforeIndex) {
            insert(w, getElement(), beforeIndex, true);
        }
    }
}


