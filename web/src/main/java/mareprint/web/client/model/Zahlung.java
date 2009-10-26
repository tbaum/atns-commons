package mareprint.web.client.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Michael Hunger
 * @since 18.07.2009
 */
public class Zahlung implements Serializable {

    public enum Typ {
        Paypal, Ueberweisung, Vorkasse, Kreditkarte;
    }

    Typ typ;
    Date erfolgt;
    Boolean offen = true;

    public void setzeBezahlt() {
        this.erfolgt = new Date();
        this.offen = false;
    }

    public void update(Typ typ) {
        this.typ=typ;
    }

    @Override
    public String toString() {
        return "Zahlung{" +
                "typ=" + typ +
                ", erfolgt=" + erfolgt +
                ", offen=" + offen +
                '}';
    }
}
