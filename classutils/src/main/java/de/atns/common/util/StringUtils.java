package de.atns.common.util;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * @author tbaum
 * @since 17.06.2010
 */
public class StringUtils {
// -------------------------- STATIC METHODS --------------------------

    public static <T> String join(final String delimiter, final Iterable<T> iterable) {
        StringBuilder b = new StringBuilder();
        for (T r : iterable) {
            if (b.length() != 0) {
                b.append(delimiter);
            }
            b.append(r.toString());
        }
        return b.toString();
    }

    public static <T> String join(final String delimiter, final T... item) {
        return join(delimiter, Arrays.asList(item));
    }

    public static <T extends Enum<T>> EnumSet<T> parseEnumSet(final Class<T> t, final String string) {
        return string != null ? parseEnumSet(t, string.split(",")) : EnumSet.noneOf(t);
    }

    public static <T extends Enum<T>> EnumSet<T> parseEnumSet(final Class<T> t, final String... string) {
        EnumSet<T> enumSet = EnumSet.noneOf(t);
        for (String s : string) {
            enumSet.add(Enum.valueOf(t, s));
        }
        return enumSet;
    }
}
