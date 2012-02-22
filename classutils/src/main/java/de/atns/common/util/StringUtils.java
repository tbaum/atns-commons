package de.atns.common.util;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * @author tbaum
 * @since 17.06.2010
 */
public class StringUtils {

    public static <T> String join(final String delimiter, final Iterable<T> iterable) {
        final StringBuilder b = new StringBuilder();
        for (final T r : iterable) {
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
        final EnumSet<T> enumSet = EnumSet.noneOf(t);
        for (final String s : string) {
            enumSet.add(Enum.valueOf(t, s));
        }
        return enumSet;
    }

    public static String toUpLower(final String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
