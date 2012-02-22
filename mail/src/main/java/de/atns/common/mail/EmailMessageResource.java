package de.atns.common.mail;

import de.atns.common.util.ImageInfo;

import javax.persistence.*;
import java.io.Serializable;

@Entity public class EmailMessageResource implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(nullable = false) private long id;
    @Basic(optional = false) private String name;
    @Basic(optional = false) private String mimeType;
    @Basic(optional = false) private byte[] data;
    @Basic private boolean embedded;
    @ManyToOne private EmailMessage message;

    public EmailMessageResource() {
    }

    public EmailMessageResource(final String name, final byte[] data) {
        this(name, ImageInfo.getMimeType(data), data, false);
    }

    public EmailMessageResource(final String name, final String mimeType, final byte[] data) {
        this(name, mimeType, data, false);
    }

    public EmailMessageResource(final String name, final String mimeType, final byte[] data, boolean embedded) {
        this.name = name;
        this.mimeType = mimeType;
        this.data = data;
        this.embedded = embedded;
    }

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

    protected void setMessage(EmailMessage message) {
        this.message = message;
    }
}
