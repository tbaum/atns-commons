package de.atns.common.mail;

import de.atns.common.util.ImageInfo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "email_spool_resource")
public class EmailResource implements Serializable {
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

    @Basic
    private boolean embedded;

// --------------------------- CONSTRUCTORS ---------------------------

    public EmailResource() {
    }

    public EmailResource(final String name, final byte[] data) {
        this(name, ImageInfo.getMimeType(data), data, false);
    }

    public EmailResource(final String name, final String mimeType, final byte[] data) {
        this(name, mimeType, data, false);
    }

    public EmailResource(final String name, final String mimeType, final byte[] data, boolean embedded) {
        this.name = name;
        this.mimeType = mimeType;
        this.data = data;
        this.embedded = embedded;
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

    public boolean isEmbedded() {
        return embedded;
    }
}
