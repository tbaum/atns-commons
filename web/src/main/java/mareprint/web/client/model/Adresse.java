package mareprint.web.client.model;

import java.io.Serializable;

/**
 * @author Michael Hunger
 * @since 18.07.2009
 */
public class Adresse implements Serializable {
    private String land;
    private String plz;
    private String ort;
    private String zusatz;
    private String strasse;

    public String getLand() {
        return land;
    }

    public String getPlz() {
        return plz;
    }

    public String getOrt() {
        return ort;
    }

    public String getZusatz() {
        return zusatz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void update(String name, String firma, String strasse, String hnr, String ort, String plz, final String land) {
        this.zusatz = firma;
        this.strasse=strasse+" "+hnr;
        this.plz = plz;
        this.ort = ort;
        this.land = land;
    }


    public String toString() {
        return "Adresse{" +
                "land='" + land + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                ", zusatz='" + zusatz + '\'' +
                ", strasse='" + strasse + '\'' +
                '}';
    }
}
