package mareprint.web.client;

import com.google.gwt.user.client.ui.TextBox;
import static mareprint.web.client.Util.persist;
import static mareprint.web.client.Util.restore;

/**
 * @author tbaum
 * @since 29.06.2009 16:32:12
 */
public class ContactComponent extends FormComponent {
// ------------------------------ FIELDS ------------------------------

    private final TextBox name = new TextBox();
    private final TextBox telefon = new TextBox();
    private final TextBox email = new TextBox();

// --------------------------- CONSTRUCTORS ---------------------------

    public ContactComponent(Mareprint app) {
        super(app);

        insertHeading("Ansprechpartner");

        name.setWidth("300px");
        addRow("Ansprechpartner", name);

        telefon.setWidth("300px");
        addRow("Telefon", telefon);

        email.setWidth("300px");
        addRow("Email", email);

        insertErrors();
        restore(name, "_c1");
        restore(telefon, "_c2");
        restore(email, "_c3");
    }

// -------------------------- OTHER METHODS --------------------------

    protected void checkFields() {
        trim(name);
        trim(telefon);
        trim(lower(email));

        persist(name, "_c1");
        persist(telefon, "_c2");
        persist(email, "_c3");

        vaMin(name, "Name", 5);
        if (vaMin(email, "Email", 1))
            vaReg(email, "Email", "^[a-z0-9._-]+@[a-z0-9._-]+\\.[a-z0-9._-]+$");
    }
}
