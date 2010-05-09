package net.customware.gwt.presenter.client.widget;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;

public class DeckDisplay extends DeckPanel implements WidgetContainerDisplay {

    @Override public void addWidget(Widget widget) {
        this.add(widget);
    }

    @Override public void removeWidget(Widget widget) {
        this.remove(widget);
    }

    @Override public void showWidget(Widget widget) {
        int index = this.getWidgetIndex(widget);
        if (index >= 0)
            this.showWidget(index);
    }

    @Override public Widget asWidget() {
        return this;
    }

    @Override public void startProcessing() {
        // Do nothing...
    }

    @Override public void stopProcessing() {
        // Do nothing...
    }

    @Override public void reset() {
        // Do nothing...
    }

}
