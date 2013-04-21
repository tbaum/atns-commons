package de.atns.common.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass public class LongIdObjectImpl2 implements LongIdObject, Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(nullable = false) private Long id;
    @Version private long version;
    private Date lastUpdateTimestamp;
    private Date createTimestamp;

    @Override public Date getCreateTimestamp() {
        return createTimestamp;
    }

    protected void setCreateTimestamp(final Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override public Long getId() {
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

    public long getVersion() {
        return version;
    }

    public void setVersion(final long version) {
        this.version = version;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final LongIdObjectImpl2 that = (LongIdObjectImpl2) o;

        if (version != that.version) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override public int hashCode() {
        if (isNew()) {
            return super.hashCode();
        }

        return (int) (id ^ (id >>> 32));
    }

    @Override public boolean isNew() {
        //TODO !!
        return id == null || id == -1;
    }

    @Override public String toString() {
        return "id=" + id + "." + version;
    }

    private int originalHashCode() {
        return super.hashCode();
    }
}
