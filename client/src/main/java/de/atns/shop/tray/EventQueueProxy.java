package de.atns.shop.tray;

import java.awt.*;

/**
 * @author tbaum
 * @since 27.09.2009 17:35:43
 */
public class EventQueueProxy extends EventQueue {
// -------------------------- OTHER METHODS --------------------------

    protected void dispatchEvent(final AWTEvent newEvent) {
        try {
            super.dispatchEvent(newEvent);
        } catch (Throwable t) {
            t.printStackTrace();
            Util.displayException(t);
        }
    }
}
