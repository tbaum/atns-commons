package de.atns.common.filter;

public interface DeleteFlagAware {

    boolean isDeleted();

    void setDeleted(final boolean deleted);
}
