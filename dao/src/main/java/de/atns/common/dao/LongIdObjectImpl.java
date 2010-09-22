package de.atns.common.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class LongIdObjectImpl implements LongIdObject, Serializable {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 4222536000799678624L;
    private Long id;
    private long version;
    private Date lastUpdateTimestamp;
    private Date createTimestamp;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override public Date getCreateTimestamp() {
        return createTimestamp;
    }

    protected void setCreateTimestamp(final Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(nullable = false)
    public Long getId() {
        return id;
    }

    @Override public void setId(final Long id) {
        this.id = id;
    }

    @Override public Date getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    protected void setLastUpdateTimestamp(final Date lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    @Version
    public long getVersion() {
        return version;
    }

    public void setVersion(final long version) {
        this.version = version;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @SuppressWarnings({"EqualsWhichDoesntCheckParameterClass"})
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        // TODO what was this for?
        //    final Class myClass = Utils.getOriginalClass(this);
        //    if (myClass != Utils.getOriginalClass(o)) return false;

        if (isNew()) {
            return originalHashCode() == ((LongIdObjectImpl) o).originalHashCode();
        }

        return id.equals(((LongIdObjectImpl) o).getId());
    }

    @Override @Transient
    public boolean isNew() {
        //TODO !!
        return id == null || id == -1;
    }

    private int originalHashCode() {
        return super.hashCode();
    }

    @Override
    public int hashCode() {
        if (isNew()) {
            return super.hashCode();
        }

        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "id=" + id + "." + version;
    }
}
