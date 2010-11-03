package de.atns.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tbaum
 * @since 14.07.2009 11:12:31
 */
public class CollectionUtils {
// -------------------------- STATIC METHODS --------------------------

    public static <TYPE> List<TYPE> clearList(final List<TYPE> children) {
        final List<TYPE> result = new ArrayList<TYPE>(children.size());
        for (final TYPE child : children) {
            if (child != null) result.add(child);
            else System.err.println("removing");
        }
        return result;
    }
}
