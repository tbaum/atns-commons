package de.atns.common.mail;

import de.atns.common.util.ImageInfo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "email_spool_resource")
public class MailResource implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 6311733671520162881L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String mimeType;

    @Basic(optional = false)
    private byte[] data;

// --------------------------- CONSTRUCTORS ---------------------------

    public MailResource() {
    }

    public MailResource(final String name, final byte[] data) {
        this.name = name;
        this.mimeType = ImageInfo.getMimeType(data);
        System.err.println("dermined type " + (data != null ? data.length : "-1") + " -> " + this.mimeType);
        this.data = data;
    }

    public MailResource(final String name, final String mimeType, final byte[] data) {
        this.name = name;
        this.mimeType = mimeType;
        this.data = data;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public byte[] getData() {
        return data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getName() {
        return name;
    }
}
