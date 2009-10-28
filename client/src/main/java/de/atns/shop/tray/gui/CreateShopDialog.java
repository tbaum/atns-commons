package de.atns.shop.tray.gui;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import de.atns.shop.tray.Prefs;
import de.atns.shop.tray.Util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.math.BigInteger;

public class CreateShopDialog extends JDialog {
// ------------------------------ FIELDS ------------------------------

    protected JButton buttonOK = new JButton("Speichern");
    protected JButton buttonCancel = new JButton("Abbrechen");
    protected boolean ok;
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();

// --------------------------- CONSTRUCTORS ---------------------------

    public CreateShopDialog() {
        setAlwaysOnTop(true);
        setModal(true);
        final DefaultFormBuilder builder = createDefaultFormBuilder();
        builder.append("Name:", nameField);
        builder.append("Shop ID:", idField);

        nameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void removeUpdate(final DocumentEvent documentEvent) {
                updateId();
            }

            @Override public void insertUpdate(final DocumentEvent documentEvent) {
                updateId();
            }

            @Override public void changedUpdate(final DocumentEvent documentEvent) {
                updateId();
            }
        });

        builder.append(createDefaultButtonPanel(), 3);
        final JPanel contentPane = builder.getPanel();
        buttonOK.addActionListener(new ActionListener() {
            @Override public void actionPerformed(final ActionEvent actionEvent) {
                ok = true;
                onOk();
                dispose();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        getRootPane().setDefaultButton(buttonOK);
        setTitle("Neuen Shop anlegen");
        setContentPane(contentPane);
        pack();
    }

    protected DefaultFormBuilder createDefaultFormBuilder() {
        final FormLayout layout = new FormLayout("p, 4dlu, fill:p:g", "");
        final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
        return builder;
    }

    private void updateId() {
        final String r = nameField.getText();
        final String id;
        if (r.length() > 0) {
            final String s = "0000000000000000" + new BigInteger(r.getBytes()).toString();
            id = s.substring(s.length() - 9);
        } else {
            id = "000000000";
        }
        idField.setText(id);
    }

    protected JPanel createDefaultButtonPanel() {
        return Util.createButtonPanel(buttonOK, buttonCancel);
    }

    protected void onOk() {
        // May be overwritten
    }

    private void onCancel() {
        ok = false;
        dispose();
    }

// -------------------------- OTHER METHODS --------------------------

    public void createShop() {
        pack();
        setVisible(true);
        if (ok) {
            Prefs.set(Prefs.NAME, idField.getText(), nameField.getText());
            Prefs.set(Prefs.SERVER, nameField.getText(), "");
        }
    }
}
