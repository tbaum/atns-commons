package de.atns.common.filter;

public class DeleteFilter implements Filter<DeleteFlagAware> {

    public static final DeleteFilter INSTANCE = new DeleteFilter();

    private DeleteFilter() {
    }

    @Override public boolean isInFilter(final DeleteFlagAware o) {
        return !o.isDeleted();
    }
}
