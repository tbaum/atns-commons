package de.atns.common.web;

import java.util.Map;

/**
 * @author tbaum
 * @since 22.10.2009
 */
public class Util {

    public static String extractParameter(final Map<String, String[]> params, final String field,
                                          final String defaultValue) {
        final String[] values = params.get(field);
        return values == null ? defaultValue : values[0];
    }
}
