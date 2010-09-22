package de.atns.common.numbergenerator;

import java.util.Calendar;
import java.util.Date;

import static java.lang.String.format;

/**
 * @author tbaum
 * @since 13.01.2010
 */
public enum NumberPeriood {
    NONE, YEAR, MONTH, DAY;

// -------------------------- OTHER METHODS --------------------------

    public String currentPrefix(Object... data) {
        final Calendar c = Calendar.getInstance();
        if (data.length > 0) {
            c.setTime((Date) data[0]);
        }

        switch (this) {
            case NONE:
                return "";
            case YEAR:
                return format("%02d", c.get(Calendar.YEAR) % 100);
            case MONTH:
                return format("%02d%02d", c.get(Calendar.YEAR) % 100, c.get(Calendar.MONTH) + 1);
            case DAY:
                return format("%02d%02d%2d", c.get(Calendar.YEAR) % 100, c.get(Calendar.MONTH) + 1,
                        c.get(Calendar.DAY_OF_MONTH) + 1);
        }
        return "";
    }
}
