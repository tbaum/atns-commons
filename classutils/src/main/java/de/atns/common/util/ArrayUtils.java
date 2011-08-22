package de.atns.common.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author tbaum
 * @since 11.05.2010
 */
public class ArrayUtils {
// -------------------------- STATIC METHODS --------------------------

    public static <T> T[] toArray(T... verarbeitung) {
        return verarbeitung;
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T[] toArray(final Class<T> clazz, final Collection<T> col) {
        final ArrayList<T> entries = new ArrayList<T>(col);
        return entries.toArray((T[]) Array.newInstance(clazz, entries.size()));
    }
}
