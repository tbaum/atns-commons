package de.atns.common.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterTemplate {
// -------------------------- STATIC METHODS --------------------------

    public static <F, E extends F> Set<E> filter(final Set<E> types, final Filter<F> filter) {
        final Set<E> fc = new HashSet<E>(10);
        for (final E aC : types) {
            if (filter.isInFilter(aC)) {
                fc.add(aC);
            }
        }
        return fc;
    }

    public static <F, E extends F> List<E> filter(final List<E> list, final Filter<F> filter) {
        final List<E> fc = new ArrayList<E>(list.size());
        for (final E aC : list) {
            if (filter.isInFilter(aC)) {
                fc.add(aC);
            }
        }
        return fc;
    }

    public static <F, E extends F> List<E> filterBack(final List<E> original, final List<E> modified, final Filter<F> filter) {
        final List<E> all = new ArrayList<E>(original);
        final List<E> ret = new ArrayList<E>(original.size());
        for (final E r : modified) {
            ret.add(r);
            all.remove(r);
        }
        for (final E r : all) {
            if (!filter.isInFilter(r)) {
                ret.add(r);
            }
        }
        return ret;
    }

    public static <F, E extends F> Set<E> filterBack(final Set<E> original, final Set<E> modified, final Filter<F> filter) {
        final Set<E> all = new HashSet<E>(original);
        final Set<E> ret = new HashSet<E>(original.size());
        for (final E r : modified) {
            ret.add(r);
            all.remove(r);
        }
        for (final E r : all) {
            if (!filter.isInFilter(r)) {
                ret.add(r);
            }
        }
        return ret;
    }
}
