package de.atns.shop.tray;

import java.io.IOException;

public interface Action {
// -------------------------- OTHER METHODS --------------------------

    boolean localOnly();

    void service() throws IOException;
}
