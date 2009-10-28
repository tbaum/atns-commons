package de.atns.shop.tray.gui;

import de.atns.shop.tray.Prefs;
import de.atns.shop.tray.Util;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.*;

public class SettingsDialog extends JDialog {
// ------------------------------ FIELDS ------------------------------

    private final JTree navigationTree = new JTree();
    private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private SettingsSubView currentView = null;
    private final CreateShopDialog createShopDialog = new CreateShopDialog();

// --------------------------- CONSTRUCTORS ---------------------------

    public SettingsDialog() {
        final JButton neuerEintragButton = new JButton("Neuer Eintrag");
        final JPanel left = new JPanel(new BorderLayout());

        updateTree();
        left.add(navigationTree, BorderLayout.CENTER);
        left.add(neuerEintragButton, BorderLayout.SOUTH);

        splitPane.setLeftComponent(left);
        splitPane.setRightComponent(new JPanel());

        navigationTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(final TreeSelectionEvent event2) {
                final Object cmp1 = event2.getPath().getLastPathComponent();
                if (cmp1 != null && cmp1 instanceof SettingsSubView && !cmp1.equals(currentView)) {
                    if (currentView != null && currentView.isDirty()) {
                        final int res2 = JOptionPane.showConfirmDialog(SettingsDialog.this,
                                "Im aktuellen Dialog wurden Daten geändert und noch nicht gespeichert?\n" +
                                        "Wollen Sie fortfahren und die Änderungen verwerfen?",
                                "Achtung!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (res2 == JOptionPane.NO_OPTION) {
                            navigationTree.setSelectionPath(event2.getOldLeadSelectionPath());
                            return;
                        }
                    }

                    setCurrentView(cmp1);
                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                onCancel();
            }
        });


        neuerEintragButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                createShopDialog.createShop();
                updateTree();
            }
        });

        splitPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setModal(true);
        setContentPane(splitPane);
        setPreferredSize(new Dimension(800, 600));
        pack();
        Util.center(this);
    }

    private void setCurrentView(final Object cmp) {
        currentView = (SettingsSubView) cmp;
        final JPanel panel = currentView.getViewPanel();
        if (panel != null) {
            currentView.populateFields();
            splitPane.setRightComponent(panel);
        } else {
            splitPane.setRightComponent(new JPanel());
        }
    }

    public void updateTree() {
        navigationTree.setModel(createTreeModel());
    }

    private TreeModel createTreeModel() {
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Einstellungen");
        final DefaultMutableTreeNode shops = new DefaultMutableTreeNode("Shops");
        root.add(shops);
        for (final String shopId1 : Prefs.getConfigNames()) {
            final DefaultMutableTreeNode shop1 = new SettingsShopView(shopId1);
            shops.add(shop1);
        }
        root.add(new SettingsLocalPrinterView());
        return new DefaultTreeModel(root);
    }

    private void onCancel() {
        dispose();
    }
}
