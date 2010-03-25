package de.atns.common.gwt.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FieldSetPanel extends ComplexPanel implements InsertPanel {
// ------------------------------ FIELDS ------------------------------

    private FieldSetPanel.LegendPanel legendPanel;

// --------------------------- CONSTRUCTORS ---------------------------

    public FieldSetPanel(Widget legend) {
        this(legend, null);
    }

    public FieldSetPanel(String text) {
        this(new Label(text));
    }

    public FieldSetPanel(String text, Widget content) {
        this(new Label(text), content);
    }

    public FieldSetPanel(Widget legend, Widget content) {
        legendPanel = new LegendPanel();
        setElement(DOM.createFieldSet());
        legendPanel.add(legend);
        add(legendPanel);
        if (content != null) {
            add(content);
        }
    }

    @Override
    public void add(Widget w) {
        add(w, getElement());
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface InsertPanel ---------------------

    public void insert(Widget w, int beforeIndex) {
        insert(w, getElement(), beforeIndex, true);
    }

// -------------------------- OTHER METHODS --------------------------

    public void addLegend(Widget legend) {
        legendPanel.add(legend);
    }

    public void addLegend(String text) {
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
        public void add(Widget w) {
            add(w, getElement());
        }

        @Override
        public void clear() {
            for (int i = 0; i < getWidgetCount(); i++) {
                getWidget(i).removeFromParent();
                remove(i);
            }
        }

        public void insert(Widget w, int beforeIndex) {
            insert(w, getElement(), beforeIndex, true);
        }
    }
}


