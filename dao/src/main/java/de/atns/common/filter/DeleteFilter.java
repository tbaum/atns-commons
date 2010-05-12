package de.atns.common.filter;

public class DeleteFilter implements Filter<DeleteFlagAware> {
// ------------------------------ FIELDS ------------------------------

    public static final DeleteFilter INSTANCE = new DeleteFilter();

// --------------------------- CONSTRUCTORS ---------------------------

    private DeleteFilter() {
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Filter ---------------------

    @Override public boolean isInFilter(final DeleteFlagAware o) {
        return !o.isDeleted();
    }
}
