package de.atns.common.gwt.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author tbaum
 * @since 22.02.12
 */
public class FieldSet extends ComplexPanel implements InsertPanel {
    public FieldSet() {
        setElement(DOM.createFieldSet());
    }

    @Override public void add(final Widget w) {
        add(w, getElement());
    }

    @Override public void insert(final Widget w, final int beforeIndex) {
        insert(w, getElement(), beforeIndex, true);
    }

    public static class Legend extends ComplexPanel implements InsertPanel {
        public Legend() {
            setElement(DOM.createLegend());
        }

        @Override public void add(final Widget w) {
            add(w, getElement());
        }

        @Override public void clear() {
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
