package de.atns.common.mail;

import de.atns.common.util.ImageInfo;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "email_spool_resource")
public class EmailResource implements Serializable {
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String mimeType;

    @Lob
    private String data;

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
        this.data = Base64.encodeBase64String(data);
        this.embedded = embedded;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public byte[] getData() {
        return Base64.decodeBase64(data);
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
