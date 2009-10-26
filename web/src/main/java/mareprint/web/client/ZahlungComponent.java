package mareprint.web.client;

import mareprint.web.client.model.Zahlung;
import com.google.gwt.user.client.ui.ListBox;

/**
 * @author tbaum
 * @since 29.06.2009 17:50:24
 */
public class ZahlungComponent extends FormComponent {
// --------------------------- CONSTRUCTORS ---------------------------
    ListBox typ=new ListBox();
    public ZahlungComponent(Mareprint mareprint) {
        super(mareprint);
        for (Zahlung.Typ zTyp : Zahlung.Typ.values()) {
            typ.addItem(zTyp.name());
        }
        typ.setWidth("300px");
        addRow("Typ",typ);
    }

// -------------------------- OTHER METHODS --------------------------

    protected void checkFields() {
        addError(typ, "Typ",typ.getSelectedIndex()==-1);
    }

    public Zahlung.Typ getTyp() {
        final int index = typ.getSelectedIndex();
        return index==-1 ? null : Zahlung.Typ.valueOf(typ.getItemText(index));
    }
}
