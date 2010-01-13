package de.atns.common.numbergenerator;

import java.util.Date;

/**
 * @author tbaum
 * @since 13.01.2010
 */
public interface NumberType {
// -------------------------- OTHER METHODS --------------------------

    Date getPeriodData();

    String name();

    NumberPeriood period();
}
