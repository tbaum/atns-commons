package mareprint.web.client;

import com.google.gwt.user.client.ui.TextBox;
import static mareprint.web.client.Util.restore;
import static mareprint.web.client.Util.persist;

/**
 * @author tbaum
 * @since 29.06.2009 16:32:12
 */
public class AdressComponent extends FormComponent {
// ------------------------------ FIELDS ------------------------------

    private final TextBox name = new TextBox();
    private final TextBox firma = new TextBox();
    private final TextBox strasse = new TextBox();
    private final TextBox hnr = new TextBox();
    private final TextBox plz = new TextBox();
    private final TextBox ort = new TextBox();
    private final String prefix;

// --------------------------- CONSTRUCTORS ---------------------------

    public AdressComponent(final Mareprint app, String prefix, String s) {
        super(app);
        this.prefix = prefix;

        insertHeading(s);

        name.setWidth("300px");
        addRow("Name", name);

        firma.setWidth("300px");
        addRow("Firma", firma);

        strasse.setWidth("250px");
        hnr.setWidth("45px");
        addRow("Strasse / Hnr", strasse, hnr);

        plz.setWidth("75px");
        ort.setWidth("220px");
        addRow("PLZ / Ort", plz, ort);

        insertErrors();

        restore(name, "_" + prefix + "1");
        restore(strasse, "_" + prefix + "2");
        restore(hnr, "_" + prefix + "3");
        restore(plz, "_" + prefix + "4");
        restore(ort, "_" + prefix + "5");
    }

// -------------------------- OTHER METHODS --------------------------

    protected void checkFields() {
        trim(name);
        trim(strasse);
        trim(hnr);
        trim(plz);
        trim(ort);

        persist(name, "_" + prefix + "1");
        persist(strasse, "_" + prefix + "2");
        persist(hnr, "_" + prefix + "3");
        persist(plz, "_" + prefix + "4");
        persist(ort, "_" + prefix + "5");

        vaMin(name, "Name", 5);
        vaMin(strasse, "Strasse", 3);
        vaMin(hnr, "Hausnummer", 1);

        if (vaLen(plz, "PLZ", 5))
            vaReg(plz, "PLZ", "^[0-9]+$");

        vaMin(ort, "Ort", 5);
    }
}