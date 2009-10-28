package de.atns.shop.tray.gui;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import de.atns.shop.tray.*;
import de.atns.shop.tray.printing.PrinterCache;
import de.atns.shop.tray.printing.SerialPortPrinterImpl;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.String.format;

public class SettingsLocalPrinterView extends DefaultMutableTreeNode implements SettingsSubView, DirtyAware {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -5849653521079149846L;

    private static final String[] LOGOS = {"DPD", "DHL_EXP", "DP_LOGO", "N_LOGO", "DE_LOGO", "ST_LOGO", "SP_LOGO"};
    private static final String[] FORMULARE = {"DPD-NN", "DPD-STD", "BI", "BI_COD", "EN", "EN_COD"};
    JPanel panel;
    private final JTextField localLabelPrinterField;
    private final JTextField localLaufzettelPrinterField;

    private boolean dirty = false;

// --------------------------- CONSTRUCTORS ---------------------------

    public SettingsLocalPrinterView() {
        super("Lokale Drucker");
        final FormLayout layout = new FormLayout("p, 4dlu, fill:p:g, 4dlu, m", "");
        final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        localLaufzettelPrinterField = new JTextField();
        localLabelPrinterField = new JTextField();

        final JButton buttonInitLabelPrinter = new JButton("Initialisieren");
        builder.append("Lokaler Labeldrucker:", localLabelPrinterField, buttonInitLabelPrinter);
        final JButton buttonInitLaufzettelPrinter = new JButton("Initialisieren");
        builder.append("Lokaler Laufzetteldrucker:", localLaufzettelPrinterField, buttonInitLaufzettelPrinter);


        final JButton buttonOK = new JButton("Speichern");
        final JButton buttonCancel = new JButton("Reset");
        builder.append(de.atns.shop.tray.Util.createButtonPanel(buttonOK, buttonCancel), 3);


        localLabelPrinterField.getDocument().addDocumentListener(new DirtyListener(this));
        localLaufzettelPrinterField.getDocument().addDocumentListener(new DirtyListener(this));


        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                populateFields();
                dirty = false;
            }
        });
        buttonInitLabelPrinter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                try {
                    initLabelPrinter();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Fehler beim Initialisieren des Druckers", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonInitLaufzettelPrinter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                try {
                    initLaufzettelPrinter();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Fehler beim Initialisieren des Druckers", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                Prefs.set(Prefs.LOCAL_LABEL_PRINTER, localLabelPrinterField.getText());
                Prefs.set(Prefs.LOCAL_LAUFZETTEL_PRINTER, localLaufzettelPrinterField.getText());
                setDirty(false);
            }
        });
        panel = builder.getPanel();
    }

    @Override
    public void populateFields() {
        this.localLabelPrinterField.setText(Prefs.getString(Prefs.LOCAL_LABEL_PRINTER));
        this.localLaufzettelPrinterField.setText(Prefs.getString(Prefs.LOCAL_LAUFZETTEL_PRINTER));
        setDirty(false);
    }

    public void initLabelPrinter() throws Exception {
        final SerialPortPrinterImpl printer = (SerialPortPrinterImpl) PrinterCache.getPrinter(localLabelPrinterField.getText());

        printer.checkStatus();

        printer.write("GK\"*\"\n".getBytes());
        printer.checkStatus();

        if (!printer.checkResponse("UG\n").isEmpty()) {
            System.err.println("nicht alle Grafiken entfernt");
            throw new RuntimeException();
        }

        printer.write("FK\"*\"\n".getBytes());
        printer.checkStatus();

        if (!printer.checkResponse("UF\n").isEmpty()) {
            System.err.println("nicht alle Formulare entfernt");
            throw new RuntimeException();
        }
        printer.clearInputQueue();

        for (final String logo : LOGOS) {
            final String fileName = format("/printerdata/%s.pcx", logo);
            System.err.println("lade " + fileName);
            printer.storeGraphics(logo, getClass().getResourceAsStream(fileName));
        }

        for (final String form : FORMULARE) {
            final String fileName = format("/printerdata/%s.frm", form);
            System.err.println("lade " + fileName);
            printer.storeForm(form, getClass().getResourceAsStream(fileName));
        }

        printer.clearInputQueue();

        if (!printer.checkResponse("UG\n", LOGOS).isEmpty()) {
            System.err.println("Fehler: Formular-Daten unvollständig im Druckerspeicher!");
            throw new RuntimeException("Fehler: Formular-Daten unvollständig im Druckerspeicher!");
        }

        if (!printer.checkResponse("UF\n", FORMULARE).isEmpty()) {
            System.err.println("Fehler: Formular-Daten unvollständig im Druckerspeicher!");
            throw new RuntimeException("Fehler: Formular-Daten unvollständig im Druckerspeicher!");
        }
    }

    private void initLaufzettelPrinter() throws Exception {
        final SerialPortPrinterImpl printer = (SerialPortPrinterImpl) PrinterCache.getPrinter(localLabelPrinterField.getText());
        printer.checkStatus();
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void setDirty(final boolean dirty) {
        this.dirty = dirty;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SettingsSubView ---------------------

    @Override
    public JPanel getViewPanel() {
        return panel;
    }
}