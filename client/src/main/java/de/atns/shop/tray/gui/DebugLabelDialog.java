package de.atns.shop.tray.gui;

import de.atns.shop.tray.PrinterTyp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DebugLabelDialog extends JDialog {
// --------------------------- CONSTRUCTORS ---------------------------

    public DebugLabelDialog(final PrinterTyp title, final String msg) {
        setAlwaysOnTop(true);
        setTitle("Dummy-print @" + title);
        final JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        final JTextArea message = new JTextArea(wrapLines(msg));
        message.setFont(Font.decode("Courier 10"));
        message.setEditable(false);
        contentPane.add(message, BorderLayout.CENTER);

        setContentPane(contentPane);
        setModal(true);

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

        pack();
        final Dimension paneSize = getSize();
        final Dimension screenSize = getToolkit().getScreenSize();
        setLocation((screenSize.width - paneSize.width) / 2, (int) ((screenSize.height - paneSize.height) * 0.45));
        setModal(false);
        setVisible(true);
    }

    private String wrapLines(final String msg) {
        final StringBuilder res = new StringBuilder();
        for (final String s : msg.split("\n")) {
            res.append(wrapLine(s)).append("\n");
        }
        return res.toString();
    }

// -------------------------- OTHER METHODS --------------------------

    private String wrapLine(String msg) {
        final StringBuilder res = new StringBuilder();
        while (msg.length() > 120) {
            res.append(msg.substring(0, 120)).append("\n");
            msg = msg.substring(120);
        }
        res.append(msg);
        return res.toString();
    }
}
