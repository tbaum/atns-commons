package de.atns.shop.tray.gui;

import javax.swing.*;
import javax.swing.tree.MutableTreeNode;

public interface SettingsSubView extends MutableTreeNode {

    JPanel getViewPanel();

    boolean isDirty();

    void populateFields();
}
