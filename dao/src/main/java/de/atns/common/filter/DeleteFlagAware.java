package de.atns.common.filter;

public interface DeleteFlagAware {
// -------------------------- OTHER METHODS --------------------------

    boolean isDeleted();

    void setDeleted(final boolean deleted);
}
