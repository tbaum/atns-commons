package mareprint.web.client.model;

import com.google.gwt.user.client.ui.TextBox;

import java.io.Serializable;

/**
 * @author Michael Hunger
 * @since 18.07.2009
 */
public class Kontakt implements Serializable {
    private String vorname;
    private String nachname;
    private String telefon;
    private String email;

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    public void update(String name, String telefon, String email) {
        this.nachname=name;
        this.telefon = telefon;
        this.email = email;
    }

    public String toString() {
        return "Kontakt{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
