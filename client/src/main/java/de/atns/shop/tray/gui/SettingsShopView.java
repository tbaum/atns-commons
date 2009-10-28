package de.atns.shop.tray.gui;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import de.atns.shop.tray.Prefs;
import de.atns.shop.tray.PrinterTyp;
import de.atns.shop.tray.RemoteShopService;
import de.atns.shop.tray.action.BankabgleichAction;
import de.atns.shop.tray.data.BankBuchung;
import de.atns.shop.tray.data.Result;
import de.atns.shop.tray.data.ShopConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.kapott.hbci.GV.HBCIJob;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SettingsShopView extends DefaultMutableTreeNode implements SettingsSubView, DirtyAware {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 6562842329450871443L;
    JPanel panel;
    private final JTextField nameField;
    private final JTextField adresseField;
    private final JTextField idField;
    private final JTextField labelDruckerField;
    private final JTextField laufzettelDruckerField;
    private final JTextField rechnungXsltField;
    private final JTextField bestellungXsltField;
    private final JTextField loginField;
    private final JTextField ktoField;
    private final JTextField blzField;
    private final JTextField bankingServerField;
    private final JPasswordField pinField;

    private boolean dirty = false;
    private final String shopId;
    private ActionListener okAction;

// --------------------------- CONSTRUCTORS ---------------------------

    public SettingsShopView(final String shopId) {
        super(Prefs.getString(Prefs.NAME, shopId));
        final FormLayout layout = new FormLayout("p, 4dlu, fill:p:g", "");
        final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        this.shopId = shopId;
        nameField = new JTextField();
        adresseField = new JTextField();
        labelDruckerField = new JTextField();
        laufzettelDruckerField = new JTextField();
        bestellungXsltField = new JTextField();
        rechnungXsltField = new JTextField();
        loginField = new JTextField();
        ktoField = new JTextField();
        blzField = new JTextField();
        pinField = new JPasswordField();
        bankingServerField = new JTextField();
        idField = new JTextField();
        idField.setEnabled(false);
        idField.setEditable(false);

        builder.append("Shop ID:", idField);
        builder.append("Name:", nameField);
        builder.append("Serveradresse:", adresseField);
        builder.append("Labeldrucker:", labelDruckerField);
        builder.append("Laufzetteldrucker:", laufzettelDruckerField);
        builder.append("XSLT für Rechnungsdruck:", rechnungXsltField);
        builder.append("XSLT für Bestellungsdruck:", bestellungXsltField);
        builder.append("Zugangskennung:", loginField);
        builder.append("Konto:", ktoField);
        builder.append("BLZ:", blzField);
//        builder.append("Banking Server:", bankingServerField);
        builder.append("PIN:", pinField);


        final JButton buttonTest = new JButton("HBCI-Test");
        final JButton buttonOK = new JButton("Speichern");
        final JButton buttonCancel = new JButton("Reset");
        builder.append(de.atns.shop.tray.Util.createButtonPanel(buttonOK, buttonCancel), 3);
        builder.append(de.atns.shop.tray.Util.createButtonPanel(buttonTest), 3);

        nameField.getDocument().addDocumentListener(new DirtyListener(this));
        adresseField.getDocument().addDocumentListener(new DirtyListener(this));
        labelDruckerField.getDocument().addDocumentListener(new DirtyListener(this));
        laufzettelDruckerField.getDocument().addDocumentListener(new DirtyListener(this));
        loginField.getDocument().addDocumentListener(new DirtyListener(this));
        ktoField.getDocument().addDocumentListener(new DirtyListener(this));
        blzField.getDocument().addDocumentListener(new DirtyListener(this));
        pinField.getDocument().addDocumentListener(new DirtyListener(this));
        rechnungXsltField.getDocument().addDocumentListener(new DirtyListener(this));
        bestellungXsltField.getDocument().addDocumentListener(new DirtyListener(this));
        bankingServerField.getDocument().addDocumentListener(new DirtyListener(this));


        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                populateFields();
                dirty = false;
            }
        });
        okAction = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                Prefs.set(Prefs.NAME, shopId, nameField.getText());
                Prefs.set(Prefs.SERVER, shopId, adresseField.getText());
                Prefs.set(Prefs.LAUFZETTEL_PRINTER, shopId, laufzettelDruckerField.getText());
                Prefs.set(Prefs.LABEL_PRINTER, shopId, labelDruckerField.getText());
                Prefs.set(Prefs.RECHNUNG_XSLT, shopId, rechnungXsltField.getText());
                Prefs.set(Prefs.BESTELLUNG_XSLT, shopId, bestellungXsltField.getText());
                Prefs.set(Prefs.BANKING_BLZ, shopId, blzField.getText());
                Prefs.set(Prefs.BANKING_LOGIN, shopId, loginField.getText());
                Prefs.set(Prefs.BANKING_KTO, shopId, ktoField.getText());
                Prefs.set(Prefs.BANKING_SERVER, shopId, bankingServerField.getText());
                final String pin = new String(pinField.getPassword());
                if (pin != null && pin.length() > 0) {
                    Prefs.set(Prefs.BANKING_PIN, shopId, pin);
                }
                setDirty(false);
            }
        };
        buttonOK.addActionListener(okAction);

        buttonTest.addActionListener(new ActionListener() {
            @Override public void actionPerformed(final ActionEvent actionEvent) {
                okAction.actionPerformed(null);
                final ShopConfiguration co = new ShopConfiguration(shopId);
                final RemoteShopService rm = new RemoteShopService(co);
                final Result res = new Result();
                List<BankBuchung> rec = new BankabgleichAction(co, rm, res) {
                    @Override protected void setQueryDate(final HBCIJob auszug) {

                    }
                }.fetchRecords();
                StringBuilder s = new StringBuilder();
                for (BankBuchung b : rec) {
                    s.append(b.toString()).append("\n");
                }
                new DebugLabelDialog(null, s.toString());
            }
        });
        panel = builder.getPanel();
    }

    @Override
    public void populateFields() {
        this.idField.setText(shopId);
        this.nameField.setText(Prefs.getString(Prefs.NAME, shopId));
        this.adresseField.setText(Prefs.getString(Prefs.SERVER, shopId));
        this.labelDruckerField.setText(Prefs.getString(Prefs.LABEL_PRINTER, shopId));
        this.laufzettelDruckerField.setText(Prefs.getString(Prefs.LAUFZETTEL_PRINTER, shopId));
        this.blzField.setText(Prefs.getString(Prefs.BANKING_BLZ, shopId));
        this.loginField.setText(Prefs.getString(Prefs.BANKING_LOGIN, shopId));
        this.ktoField.setText(Prefs.getString(Prefs.BANKING_KTO, shopId));
        this.pinField.setText(Prefs.getString(Prefs.BANKING_PIN, shopId));
        this.bankingServerField.setText(Prefs.getString(Prefs.BANKING_SERVER, shopId));
        this.bestellungXsltField.setText(Prefs.getString(Prefs.BESTELLUNG_XSLT, shopId));
        this.rechnungXsltField.setText(Prefs.getString(Prefs.RECHNUNG_XSLT, shopId));
        setDirty(false);
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

// -------------------------- INNER CLASSES --------------------------

    private class TestLabelActionListner implements ActionListener {
        private final String data1;

        private TestLabelActionListner(final String data) {
            this.data1 = data;
        }

        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            try {
                final PostMethod post = new PostMethod(labelDruckerField.getText());
                final HttpClient client = new HttpClient();

                final NameValuePair[] data = {
                        new NameValuePair("action", "doPrint"),
                        new NameValuePair("data", data1),
                        new NameValuePair("printer", PrinterTyp.LABEL.name()),
                        new NameValuePair("authtoken", String.valueOf("0"))
                };

                post.setRequestBody(data);
                client.executeMethod(post);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
