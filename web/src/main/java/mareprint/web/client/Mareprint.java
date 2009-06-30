package mareprint.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;
import static com.google.gwt.user.client.ui.RootPanel.get;
import static com.google.gwt.user.client.ui.RootPanel.getBodyElement;
import static mareprint.web.client.Util.horizontal;

/**
 * @author tbaum
 * @since 26.06.2009
 */
public class Mareprint implements EntryPoint {
// ------------------------------ FIELDS ------------------------------

    private static final String INVOICE_DELIVERY_TITLE = "Rechnungs- / Lieferanschrift";
    private static final String INVOICE_TITLE = "Rechnungsanschrift";

    private final ContactComponent contact = new ContactComponent(this);
    private final AdressComponent invoiceAddress = new AdressComponent(this, "i", INVOICE_DELIVERY_TITLE);
    private final AdressComponent deliveryAddress = new AdressComponent(this, "d", "Lieferanschrift");
    private final CheckBox deliveryAddressDiffers = new CheckBox("abweichende Lieferanschrift?");
    private final ZahlungComponent payment = new ZahlungComponent(this);

    private final FileUploadComponent fileFileUpload = new FileUploadComponent(this);
    private final TextArea debug = new TextArea();


// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
        final RootPanel rootPanel = get();


        rootPanel.add(fileFileUpload);

        rootPanel.add(contact);

        deliveryAddressDiffers.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            public void onValueChange(final ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                deliveryAddress.setVisible(deliveryAddressDiffers.getValue());
                invoiceAddress.setHeading(deliveryAddressDiffers.getValue() ? INVOICE_TITLE : INVOICE_DELIVERY_TITLE);
                validate();
            }
        });

        deliveryAddress.setVisible(false);
        rootPanel.add(horizontal(invoiceAddress, deliveryAddress));
        rootPanel.add(deliveryAddressDiffers);


        rootPanel.add(payment);
        debug.setHeight("150px");
        debug.setWidth("500px");
        debug.setReadOnly(true);
        debug.addStyleName("debugPanel");
        rootPanel.add(debug);

        getBodyElement().removeChild(get("loading").getElement());
    }

// -------------------------- OTHER METHODS --------------------------

    public void validate() {
        boolean dataOk = !fileFileUpload.hasErrors();
        boolean contactOk = !contact.hasErrors();
        boolean addressOk = !invoiceAddress.hasErrors() && (deliveryAddressDiffers.getValue() && !deliveryAddress.hasErrors() || !deliveryAddressDiffers.getValue());

        String dbg = "";

        dbg += dataOk ? "upload ok\n" : "upload incomplete\n";

        for (String s : fileFileUpload.getHashCodes()) {
            dbg += " got file --> " + s + "\n";
        }
        for (String info : fileFileUpload.getFileInfos()) {
            dbg += info+"\n";
        }

        dbg += contactOk ? "contact ok\n" : "contact incomplete\n";
        dbg += addressOk ? "address ok\n" : "address incomplete\n";

        debug.setValue(dbg);
    }
}