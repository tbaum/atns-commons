package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import de.atns.common.gwt.client.event.StartLoadingEvent;
import de.atns.common.gwt.client.event.StopLoadingEvent;

import static com.google.gwt.dom.client.Style.BorderStyle.SOLID;
import static com.google.gwt.dom.client.Style.Position.ABSOLUTE;
import static com.google.gwt.dom.client.Style.Unit.PX;

/**
 * @author tbaum
 * @since 07.12.2009
 */
@Singleton
public class Loader extends FlowPanel {

    private final Label countLabel;
    private int counter;
    private EventBus eventBus;

    @Inject
    protected Loader(EventBus eventBus) {
        this.eventBus = eventBus;
        countLabel = new Label("lol");

        add(new Image("spinner.gif"));
        add(countLabel);

        countLabel.setVisible(false);

        final Style style = getElement().getStyle();
        style.setPosition(ABSOLUTE);
        style.setTop(30, PX);
        style.setRight(20, PX);
        style.setBackgroundColor("#ffffff");
        style.setBorderWidth(2, PX);
        style.setBorderStyle(SOLID);
        style.setBorderColor("black");
        style.setPadding(4, PX);
        setVisible(false);
    }

    public void counter() {
        if(counter == 0) {
            eventBus.fireEvent(new StartLoadingEvent());
        }
        setVisible(true);
        updateCounterText();
        counter++;
    }

    public void decounter() {
        counter--;
        if (counter < 0) counter = 0;
        updateCounterText();
        if (counter == 0) {
            eventBus.fireEvent(new StopLoadingEvent());
            setVisible(false);
        }
    }

    private void updateCounterText() {
        countLabel.setText(String.valueOf(counter));
    }
}
