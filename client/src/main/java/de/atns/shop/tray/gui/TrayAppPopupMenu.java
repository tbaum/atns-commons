package de.atns.shop.tray.gui;

import de.atns.shop.tray.Util;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author tbaum
 * @since 27.09.2009 18:14:16
 */
public class TrayAppPopupMenu extends PopupMenu {
// ------------------------------ FIELDS ------------------------------

    private final SettingsDialog settingsDialog = new SettingsDialog();

// --------------------------- CONSTRUCTORS ---------------------------

    public TrayAppPopupMenu() throws HeadlessException {
        final ActionListener exitListener = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        };

        final MenuItem defaultItem = new MenuItem("Exit");
        final MenuItem configItem = new MenuItem("Konfiguration");
        configItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent event) {
                EventQueue.invokeLater(new Runnable() {
                    @Override public void run() {  Util. center(settingsDialog);
                        settingsDialog.requestFocus();
                    }
                });
                settingsDialog.setVisible(true);
            }
        });
        defaultItem.addActionListener(exitListener);
        add(configItem);
        add(defaultItem);
    }
}
