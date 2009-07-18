package mareprint.web.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Michael Hunger
 * @since 18.07.2009
 */
public class Auftrag implements Serializable {

    private String rechnungsNr = ""+System.currentTimeMillis();

    private Adresse lieferAdresse = new Adresse();
    private Adresse rechnungsAdresse = new Adresse();
    private Kontakt kontakt = new Kontakt();
    private Zahlung zahlung = new Zahlung();
    private List<Position> positionen = new ArrayList<Position>();
    private Integer positionsPreis = 0;
    private Integer versand = 0;
    private Integer nettoPreis = 0;
    private Integer bruttoPreis = 0;
    private Boolean differentDeliveryAddress;

    public Adresse getLieferAdresse() {
        return lieferAdresse;
    }

    public Kontakt getKontakt() {
        return kontakt;
    }

    public int getBruttoPreis() {
        return bruttoPreis;
    }

    public int getVersand() {
        return versand;
    }

    public String getRechnungsNr() {
        return rechnungsNr;
    }


    public void setzeBezahlt(String tx, String status, int amount) {
        zahlung.setzeBezahlt();
    }

    public void setDifferentDeliveryAddress(Boolean differentDeliveryAddress) {
        this.differentDeliveryAddress = differentDeliveryAddress;
    }

    public Adresse getRechnungsAddresse() {
        return rechnungsAdresse;
    }

    public Zahlung getZahlung() {
        return zahlung;
    }

    public String toString() {
        return "Auftrag{" +
                "rechnungsNr='" + rechnungsNr + '\'' +
                ", lieferAdresse=" + lieferAdresse +
                ", rechnungsAdresse=" + rechnungsAdresse +
                ", kontakt=" + kontakt +
                ", zahlung=" + zahlung +
                ", positionen=" + positionen +
                ", positionsPreis=" + positionsPreis +
                ", versand=" + versand +
                ", nettoPreis=" + nettoPreis +
                ", bruttoPreis=" + bruttoPreis +
                ", differentDeliveryAddress=" + differentDeliveryAddress +
                '}';
    }
}
