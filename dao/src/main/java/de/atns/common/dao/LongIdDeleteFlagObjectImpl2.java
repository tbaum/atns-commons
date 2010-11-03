package de.atns.common.dao;

import de.atns.common.filter.DeleteFlagAware;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class LongIdDeleteFlagObjectImpl2 extends LongIdObjectImpl2 implements DeleteFlagAware {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 4081932716830230456L;

    private boolean deleted;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override public boolean isDeleted() {
        return deleted;
    }

    @Override public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public String toString() {
        return deleted ? "(" + super.toString() + ")" : super.toString();
    }
}
