package de.atns.common.dao;

import de.atns.common.filter.DeleteFlagAware;

import javax.persistence.MappedSuperclass;

@MappedSuperclass public class LongIdDeleteFlagObjectImpl2 extends LongIdObjectImpl2 implements DeleteFlagAware {

    private boolean deleted;

    @Override public boolean isDeleted() {
        return deleted;
    }

    @Override public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }

    @Override public String toString() {
        return deleted ? "(" + super.toString() + ")" : super.toString();
    }
}
