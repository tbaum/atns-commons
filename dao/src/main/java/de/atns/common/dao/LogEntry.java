package de.atns.common.dao;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author tbaum
 * @since 9.07.2011
 */
@Entity public class LogEntry {
// ------------------------------ FIELDS ------------------------------

    @Id @GeneratedValue(strategy = IDENTITY) private Long id;
    private Date datum;
    private String benutzer;
    private String entity;
    @Lob private String daten;
    @Enumerated(STRING) private Action action;
    private String entityId;
    private String entityVersion;

// --------------------------- CONSTRUCTORS ---------------------------

    protected LogEntry() {
    }

    public LogEntry(String benutzer, Action action, String entity, String entityId, String entityVersion, String daten) {
        this.benutzer = benutzer;
        this.action = action;
        this.entity = entity;
        this.daten = daten;
        this.datum = new Date();
        this.entityId = entityId;
        this.entityVersion = entityVersion;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Action getAction() {
        return action;
    }

    public String getBenutzer() {
        return benutzer;
    }

    public String getDaten() {
        return daten;
    }

    public Date getDatum() {
        return datum;
    }

    public String getEntity() {
        return entity;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getEntityVersion() {
        return entityVersion;
    }

    public Long getId() {
        return id;
    }

// -------------------------- ENUMERATIONS --------------------------

    public enum Action {
        remove, persist, update
    }
}
