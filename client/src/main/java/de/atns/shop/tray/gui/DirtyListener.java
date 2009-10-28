package de.atns.shop.tray.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DirtyListener implements DocumentListener {
    private final DirtyAware view;

    public DirtyListener(final DirtyAware view) {
        this.view = view;
    }

    @Override
    public void insertUpdate(final DocumentEvent event) {
        view.setDirty(true);
    }

    @Override
    public void removeUpdate(final DocumentEvent event) {
        view.setDirty(true);
    }

    @Override
    public void changedUpdate(final DocumentEvent event) {
        view.setDirty(true);
    }
}
