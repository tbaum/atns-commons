package mareprint.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;
import static com.google.gwt.user.client.ui.RootPanel.get;
import static com.google.gwt.user.client.ui.RootPanel.getBodyElement;
import static mareprint.web.client.Util.horizontal;
import mareprint.web.client.model.Adresse;
import mareprint.web.client.model.Auftrag;

/**
 * @author tbaum
 * @since 26.06.2009
 */
public class Mareprint implements EntryPoint, ClickHandler {
// ------------------------------ FIELDS ------------------------------

    private static final String INVOICE_DELIVERY_TITLE = "Rechnungs- / Lieferanschrift";
    private static final String INVOICE_TITLE = "Rechnungsanschrift";

    Button[] steps = new Button[]{
            new Button("Upload", new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    visible(false, contact, invoiceAddress, deliveryAddressDiffers, deliveryAddress, payment);
                    visible(true, fileFileUpload);
                }
            }),
            new Button("Kontakt", new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    visible(false, fileFileUpload, invoiceAddress, deliveryAddressDiffers, deliveryAddress, payment);
                    visible(true, contact);
                }
            }),
            new Button("Adressen", new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    visible(false, fileFileUpload, contact, payment);
                    visible(true, invoiceAddress, deliveryAddressDiffers);
                    if (deliveryAddressDiffers.getValue()) visible(true, deliveryAddress);
                }
            }),
            new Button("Zahlung", new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    visible(false, fileFileUpload, invoiceAddress, deliveryAddressDiffers, deliveryAddress, contact);
                    visible(true, payment);
                }
            }),
            new Button("Bestätigung", new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    visible(false, fileFileUpload, invoiceAddress, deliveryAddressDiffers, deliveryAddress, contact, payment);
                    // todo summary
                }
            })
    };
    private final Auftrag auftrag = new Auftrag();
    private final ContactComponent contact = new ContactComponent(this);
    private final AdressComponent invoiceAddress = new AdressComponent(this, "i", INVOICE_DELIVERY_TITLE);
    private final AdressComponent deliveryAddress = new AdressComponent(this, "d", "Lieferanschrift");
    private final CheckBox deliveryAddressDiffers = new CheckBox("abweichende Lieferanschrift?");
    private final ZahlungComponent payment = new ZahlungComponent(this);

    private final FileUploadComponent fileFileUpload = new FileUploadComponent(this);
    private final TextArea debug = new TextArea();

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ClickHandler ---------------------

    public void onClick(ClickEvent clickEvent) {
        final Object source = clickEvent.getSource();
    }

// --------------------- Interface EntryPoint ---------------------


    public void onModuleLoad() {
        final RootPanel rootPanel = get();

        rootPanel.add(horizontal(steps));
        rootPanel.add(fileFileUpload);

        rootPanel.add(contact);

        deliveryAddressDiffers.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            public void onValueChange(final ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                deliveryAddress.setVisible(deliveryAddressDiffers.getValue());
                invoiceAddress.setHeading(deliveryAddressDiffers.getValue() ? INVOICE_TITLE : INVOICE_DELIVERY_TITLE);
                validate();
            }
        });

        rootPanel.add(horizontal(invoiceAddress, deliveryAddress));
        rootPanel.add(deliveryAddressDiffers);


        rootPanel.add(payment);
        debug.setHeight("150px");
        debug.setWidth("500px");
        debug.setReadOnly(true);
        debug.addStyleName("debugPanel");
        rootPanel.add(debug);

        getBodyElement().removeChild(get("loading").getElement());

        visible(false, contact, invoiceAddress, deliveryAddressDiffers, deliveryAddress, payment);
    }

// -------------------------- OTHER METHODS --------------------------

    public void validate() {
        boolean dataOk = !fileFileUpload.hasErrors();
        boolean contactOk = !contact.hasErrors();
        final Boolean differentDeliveryAddress = deliveryAddressDiffers.getValue();
        boolean addressOk = !invoiceAddress.hasErrors() && (differentDeliveryAddress && !deliveryAddress.hasErrors() || !differentDeliveryAddress);

        String dbg = "";

        dbg += dataOk ? "upload ok\n" : "upload incomplete\n";

        for (String s : fileFileUpload.getHashCodes()) {
            dbg += " got file --> " + s + "\n";
        }
        for (String info : fileFileUpload.getFileInfos()) {
            dbg += info + "\n";
        }

        dbg += contactOk ? "contact ok\n" : "contact incomplete\n";
        dbg += addressOk ? "address ok\n" : "address incomplete\n";

        if (contactOk) {
            auftrag.getKontakt().update(contact.getName(), contact.getTelefon(), contact.getEmail());
        }
        if (differentDeliveryAddress) {
            auftrag.setDifferentDeliveryAddress(differentDeliveryAddress);
            updateAdresse(deliveryAddress, auftrag.getLieferAdresse());
        }
        updateAdresse(invoiceAddress, auftrag.getRechnungsAddresse());
        if (!payment.hasErrors()) {
            auftrag.getZahlung().update(payment.getTyp());
        }
        dbg += auftrag.toString();

        debug.setValue(dbg);
    }

    private void updateAdresse(AdressComponent addressComponent, Adresse addresse) {
        if (addressComponent.hasErrors()) return;
        addresse.update(addressComponent.getName(), addressComponent.getFirma(), addressComponent.getStrasse(), addressComponent.getHnr(), addressComponent.getOrt(), addressComponent.getPlz(), addressComponent.getLand());
    }

    private void visible(boolean visible, Widget... widgets) {
        for (Widget widget : widgets) {
            widget.setVisible(visible);
        }
    }
}