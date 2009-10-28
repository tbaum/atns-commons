package de.atns.shop.tray;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import de.atns.shop.tray.data.ShopConfiguration;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import static java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.event.*;

@Singleton public class EnterWeightDialog extends JDialog implements HotkeyListener, KeyEventDispatcher {
// ------------------------------ FIELDS ------------------------------

    private static final int ACTIVATE = 88;
    private boolean showError = false;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField barcode;
    private JTextField shopName;
    private JTextField lieferschein;
    private JTextField adresseAnrede;
    private JTextField adresseFirma;
    private JTextField adresseName;
    private JTextField adresseStrasse;
    private JTextField adresseHnr;
    private JTextField adresseLand;
    private JTextField adressePlz;
    private JTextField adresseOrt;
    private JTextField gewicht;

    private JTextField produkt;
    private JTextField routing;
    private StringBuilder scannBuffer = null;

    private String dataLoaded = null;

    private boolean stateOk = false;
    //    private final RemoteShopService remote;
    private ShopConfiguration currentShop;
    private String doc;

    private final AuthtokenCache cache;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public EnterWeightDialog(final AuthtokenCache cache) {
        this.cache = cache;
//        this.cache = cache;
        //       this.remote = remote;
        $$$setupUI$$$();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Gewicht Eingeben (Label wird gedruckt)");
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                onCancel();
            }
        });


        //    gewicht.setFormatterFactory(new DefaultFormatterFactory());
        // gewicht.setValue((double) 0);

        try {
            final JIntellitype instance = JIntellitype.getInstance();
            instance.addHotKeyListener(this);
            //    instance.addIntellitypeListener(this);
            System.err.println("JIntellitype initialized");
            instance.registerHotKey(ACTIVATE, JIntellitype.MOD_ALT + JIntellitype.MOD_CONTROL, 123);
        } catch (RuntimeException ex) {
            System.err.println("Either you are not on Windows, or there is a problem with the JIntellitype library!");
        }
        getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
        // setAlwaysOnTop(true);
        pack();

        center();
    }

    private void createUIComponents() {
        gewicht = new JTextField(
                new PlainDocument() {
                    public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
                        if (str.equals("")) return;
                        if (str.matches("[0-9]+") || (str.matches("[,]+") && !gewicht.getText().contains(","))) {
                            super.insertString(offs, str, a);
                            updateOkButton();
                        } else {
                            getDefaultToolkit().beep();
                        }
                    }
                }, "", 3);
    }

    private void updateOkButton() {
        stateOk = false;
        try {
            if (getGewicht() > 0 && dataLoaded != null) stateOk = true;
        } catch (NumberFormatException ignored) {
        }

        buttonOK.setEnabled(stateOk);
    }

    private Double getGewicht() {
        return Double.parseDouble(gewicht.getText().replaceAll(",", "."));
    }

    private void onOK() {
        try {
            final Double d = getGewicht();
            int repeat = 2;
            while (repeat > 0) {
                try {
                    final RemoteShopService remote = new RemoteShopService(currentShop);
                    //   cache.update(currentShop);
                    final String res = remote.updateGewicht(doc, d);
                    System.err.println("res=" + res);

                    if (!res.equals("OK")) {
                        showError("kann Gewicht für Lieferschein " + doc + " nicht eintragen\n" + res);
                    }
                    repeat = 0;


                    final String data = remote.getLabelForId(doc);

                    final String result = remote.sendTo(currentShop.getLabelPrinterUrl(), data, PrinterTyp.LABEL);

                    System.err.println("'" + result + "'");
                    
                    if (!result.equals("jsonResponse(\"{\\\"success\\\":true}\");")) {
                        showError("Fehler beim Druchen des Labels für Lieferschein " + doc + "\n" + result);
                    }
                    //  } catch (IOException e) {
                    //    showError("kann Gewicht für Lieferschein " + doc + " nicht eintragen\n" + e.getMessage());
                    //     repeat = 0;
                }
                catch (RemoteAuthenticationException e) {
                    cache.authenticate(currentShop);
                    repeat--;
                } catch (RemoteException e) {
                    showError("kann Gewicht für Lieferschein " + doc + " nicht eintragen\n" + e.getResponse());
                    repeat = 0;
                }
            }
            System.err.println("prs::" + d);
// add your code here
            dispose();
        } catch (NumberFormatException ignored) {
            getDefaultToolkit().beep();
        }
    }

    private void showError(final String msg) {
        showError = true;
        JOptionPane.showMessageDialog(this, msg, "Fehler", JOptionPane.ERROR_MESSAGE);
        showError = false;
        setVisible(false);
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void center() {
        final Dimension screenSize = getDefaultToolkit().getScreenSize();
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
            }
        });
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HotkeyListener ---------------------

    public void onHotKey(final int aIdentifier) {
        System.err.println("WM_HOTKEY message received " + Integer.toString(aIdentifier));
        //  setVisible(false);

        scannBuffer = new StringBuilder();
//        barcodeField.setEditable(true);
        //   barcodeField.setText("");
        setVisible(true);
        toFront();
        requestFocus();
        center();


        //      barcodeField.requestFocus();
    }

// --------------------- Interface KeyEventDispatcher ---------------------

    @Override public boolean dispatchKeyEvent(final KeyEvent e) {
        if (scannBuffer != null) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    final String s = scannBuffer.toString();
                    EventQueue.invokeLater(new Runnable() {
                        @Override public void run() {
                            scanFinished(s);
                        }
                    });
                    scannBuffer = null;
                    return true;
                }
                if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                    scannBuffer.append(e.getKeyChar());
                    return true;
                }
            }
            return true;
        }

        if (e.getID() == KeyEvent.KEY_PRESSED && isVisible() && !showError) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER && stateOk) {
                onOK();
                return true;
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                onCancel();
                return true;
            }
        }

        return false;
    }

// -------------------------- OTHER METHODS --------------------------

    private void scanFinished(final String s) {
        clearForm();


        barcode.setText(s);
        if (s.length() < 10) {
            showError("Barcode nicht erkannt!");
            return;
        }
        final String shopId;
        try {
            shopId = s.substring(0, 9);
            this.doc = s.substring(9);
        } catch (Exception e) {
            showError("Barcode nicht erkannt!\n\n" + e.getMessage());
            return;
        }


        currentShop = new ShopConfiguration(shopId, cache);

        if (currentShop.getName() == null) {
            showError("Shop mit Kennung '" + shopId + "' nicht konfiguriert!!");
            return;
        }

        shopName.setText(currentShop.getName());
        lieferschein.setText(String.valueOf(doc));

        setTitle("lade Lieferschein " + doc);
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                gewicht.requestFocus();
            }
        });
        loadDocument();
    }

    private void clearForm() {
        setTitle("warte auf Barcodedaten");
        dataLoaded = null;
        updateOkButton();
        barcode.setText("");
        shopName.setText("");
        lieferschein.setText("");
        adresseAnrede.setText("");
        adresseFirma.setText("");
        adresseName.setText("");
        adresseStrasse.setText("");
        adresseHnr.setText("");
        adresseLand.setText("");
        adressePlz.setText("");
        adresseOrt.setText("");
        produkt.setText("");
        routing.setText("");
        gewicht.setText("");
    }

    private void loadDocument() {
        new Thread() {
            @Override public void run() {
                int repeat = 2;
                while (repeat > 0) {
                    try {

                        final RemoteShopService remote = new RemoteShopService(currentShop);
                        final String res = remote.getLieferscheinDetails(doc);

                        final String[] r = res.split("\n");
                        adresseAnrede.setText(r[0]);
                        adresseFirma.setText(r[1]);
                        adresseName.setText(r[2]);
                        adresseStrasse.setText(r[3]);
                        adresseHnr.setText(r[4]);
                        adresseLand.setText(r[5]);
                        adressePlz.setText(r[6]);
                        adresseOrt.setText(r[7]);
                        produkt.setText(r[8]);
                        routing.setText(r[9]);
                        repeat = 0;


                        dataLoaded = doc;
                        setTitle("Bitte Gewicht eingeben");
                        updateOkButton();
                        return;
                    }
                    catch (RemoteAuthenticationException e) {
                        cache.authenticate(currentShop);
                        repeat--;
                    } catch (RemoteException e) {
                        showError("kann Lieferschein " + doc + " nicht laden\n" + e.getResponse());
                        repeat = 0;
                    }
                }

                showError("kann Lieferschein " + doc + " nicht laden ");

            }
        }.start();
        //      dataLoaded = true;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new FormLayout("left:150px:noGrow,left:4dlu:noGrow,fill:150px:noGrow,left:4dlu:noGrow,fill:150px:noGrow", "center:max(d;4px):noGrow,top:3dlu:noGrow,center:max(d;4px):noGrow,top:3dlu:noGrow,center:max(d;4px):noGrow,top:3dlu:noGrow,center:max(d;4px):noGrow,top:3dlu:noGrow,center:max(d;4px):noGrow,top:3dlu:noGrow,center:max(d;50px):grow,top:3dlu:noGrow,center:max(d;4px):noGrow"));
        final JLabel label1 = new JLabel();
        label1.setText("Barcode:");
        CellConstraints cc = new CellConstraints();
        contentPane.add(label1, new CellConstraints(1, 1, 1, 1, CellConstraints.LEFT, CellConstraints.TOP, new Insets(6, 5, 0, 0)));
        barcode = new JTextField();
        barcode.setEditable(false);
        barcode.setEnabled(true);
        contentPane.add(barcode, new CellConstraints(3, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
        final JLabel label2 = new JLabel();
        label2.setText("Shop / Lieferschein");
        contentPane.add(label2, new CellConstraints(1, 3, 1, 1, CellConstraints.DEFAULT, CellConstraints.TOP, new Insets(6, 5, 0, 0)));
        shopName = new JTextField();
        shopName.setEditable(false);
        shopName.setEnabled(true);
        contentPane.add(shopName, new CellConstraints(3, 3, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FormLayout("fill:140px:noGrow,left:4dlu:noGrow,fill:d:grow,left:4dlu:noGrow,fill:d:grow,left:4dlu:noGrow,fill:d:grow,left:4dlu:noGrow,fill:d:grow", "center:d:noGrow,top:1dlu:noGrow,center:d:noGrow,top:1dlu:noGrow,center:d:noGrow,top:1dlu:noGrow,center:d:noGrow,top:1dlu:noGrow,center:max(d;4px):noGrow"));
        panel1.setEnabled(true);
        contentPane.add(panel1, new CellConstraints(1, 5, 5, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets(0, 5, 0, 5)));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null));
        adressePlz = new JTextField();
        adressePlz.setEditable(false);
        adressePlz.setEnabled(true);
        panel1.add(adressePlz, cc.xy(5, 9, CellConstraints.FILL, CellConstraints.DEFAULT));
        adresseLand = new JTextField();
        adresseLand.setEditable(false);
        adresseLand.setEnabled(true);
        panel1.add(adresseLand, cc.xy(3, 9, CellConstraints.FILL, CellConstraints.DEFAULT));
        adresseAnrede = new JTextField();
        adresseAnrede.setEditable(false);
        adresseAnrede.setEnabled(true);
        panel1.add(adresseAnrede, cc.xyw(3, 1, 3, CellConstraints.FILL, CellConstraints.DEFAULT));
        adresseFirma = new JTextField();
        adresseFirma.setEditable(false);
        adresseFirma.setEnabled(true);
        panel1.add(adresseFirma, cc.xyw(3, 3, 7, CellConstraints.FILL, CellConstraints.DEFAULT));
        adresseName = new JTextField();
        adresseName.setEditable(false);
        adresseName.setEnabled(true);
        panel1.add(adresseName, cc.xyw(3, 5, 7, CellConstraints.FILL, CellConstraints.DEFAULT));
        adresseStrasse = new JTextField();
        adresseStrasse.setEditable(false);
        adresseStrasse.setEnabled(true);
        panel1.add(adresseStrasse, cc.xyw(3, 7, 5, CellConstraints.FILL, CellConstraints.DEFAULT));
        adresseHnr = new JTextField();
        adresseHnr.setEditable(false);
        adresseHnr.setEnabled(true);
        panel1.add(adresseHnr, cc.xy(9, 7, CellConstraints.FILL, CellConstraints.DEFAULT));
        adresseOrt = new JTextField();
        adresseOrt.setEditable(false);
        adresseOrt.setEnabled(true);
        panel1.add(adresseOrt, cc.xyw(7, 9, 3, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label3 = new JLabel();
        label3.setText("Lieferadresse");
        panel1.add(label3, new CellConstraints(1, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.TOP, new Insets(6, 5, 0, 0)));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FormLayout("fill:d:grow,left:4dlu:noGrow,fill:max(d;4px):noGrow", "center:d:grow"));
        contentPane.add(panel2, cc.xyw(1, 13, 5));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FormLayout("fill:d:grow,left:4dlu:noGrow,fill:d:grow", "center:d:grow"));
        panel2.add(panel3, cc.xy(3, 1));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel3.add(buttonOK, cc.xy(1, 1));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel3.add(buttonCancel, cc.xy(3, 1));
        final JLabel label4 = new JLabel();
        label4.setText("Produkt / Routing");
        contentPane.add(label4, new CellConstraints(1, 7, 1, 1, CellConstraints.DEFAULT, CellConstraints.TOP, new Insets(6, 5, 0, 0)));
        final JLabel label5 = new JLabel();
        label5.setText("Gewicht");
        contentPane.add(label5, new CellConstraints(1, 9, 1, 1, CellConstraints.DEFAULT, CellConstraints.TOP, new Insets(6, 5, 0, 0)));
        gewicht.setBackground(new Color(-6684673));
        gewicht.setHorizontalAlignment(4);
        contentPane.add(gewicht, cc.xy(3, 9, CellConstraints.FILL, CellConstraints.DEFAULT));
        lieferschein = new JTextField();
        lieferschein.setEditable(false);
        lieferschein.setEnabled(true);
        contentPane.add(lieferschein, new CellConstraints(5, 3, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
        produkt = new JTextField();
        produkt.setEditable(false);
        produkt.setEnabled(true);
        contentPane.add(produkt, cc.xy(3, 7, CellConstraints.FILL, CellConstraints.DEFAULT));
        routing = new JTextField();
        routing.setEditable(false);
        routing.setEnabled(true);
        contentPane.add(routing, new CellConstraints(5, 7, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT, new Insets(0, 0, 0, 5)));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
