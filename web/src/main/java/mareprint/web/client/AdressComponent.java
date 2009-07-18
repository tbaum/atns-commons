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
    private final TextBox land = new TextBox();
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
        land.setWidth("300px");
        land.setValue("DE");
        addRow("Land",land);

        insertErrors();

        restore(name, "_" + prefix + "1");
        restore(strasse, "_" + prefix + "2");
        restore(hnr, "_" + prefix + "3");
        restore(plz, "_" + prefix + "4");
        restore(ort, "_" + prefix + "5");
        restore(land, "_" + prefix + "6");
    }

// -------------------------- OTHER METHODS --------------------------

    protected void checkFields() {
        trim(name);
        trim(strasse);
        trim(hnr);
        trim(plz);
        trim(ort);
        trim(land);

        persist(name, "_" + prefix + "1");
        persist(strasse, "_" + prefix + "2");
        persist(hnr, "_" + prefix + "3");
        persist(plz, "_" + prefix + "4");
        persist(ort, "_" + prefix + "5");
        persist(land, "_" + prefix + "6");

        validateMinLength(name, "Name", 5);
        validateMinLength(strasse, "Strasse", 3);
        validateMinLength(hnr, "Hausnummer", 1);
        if (validateMinLength(land, "Land", 2)) {
            validateIn(land, "Land", "DE","AT","CH");
        }

        if (validateLength(plz, "PLZ", 5))
            validateRegexp(plz, "PLZ", "^[0-9]+$");

        validateMinLength(ort, "Ort", 5);
    }

    public String getName() {
        return name.getText();
    }

    public String getFirma() {
        return firma.getText();
    }

    public String getStrasse() {
        return strasse.getText();
    }

    public String getHnr() {
        return hnr.getText();
    }

    public String getPlz() {
        return plz.getText();
    }

    public String getOrt() {
        return ort.getText();
    }

    public String getLand() {
        return land.getText();
    }
}