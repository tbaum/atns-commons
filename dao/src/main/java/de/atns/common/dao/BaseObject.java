package de.atns.common.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass public class BaseObject implements Serializable {
// ------------------------------ FIELDS ------------------------------

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Version
    private long version;

    private Date lastUpdateTimestamp;
    private Date createTimestamp;

// --------------------- GETTER / SETTER METHODS ---------------------

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public Long getId() {
        return id;
    }

    public Date getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public long getVersion() {
        return version;
    }

// -------------------------- OTHER METHODS --------------------------

    @PreUpdate
    @PrePersist
    private void updateTimeStamps() {
        lastUpdateTimestamp = new Date();
        if (createTimestamp == null) {
            createTimestamp = lastUpdateTimestamp;
        }
    }
}