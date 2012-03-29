package de.atns.common.gwt.datefilter.client;

import org.gwttime.time.DateMidnight;
import org.gwttime.time.DateTime;

import java.util.Date;

import static org.gwttime.time.DateTimeConstants.SUNDAY;

/**
 * @author mwolter
 * @since 15.03.12 16:25
 */
public class DateUtils {

    public static DateMidnight getFirstDayOfMonth(DateMidnight today) {
        return today.dayOfMonth().setCopy(1);
    }

    public static DateMidnight clearTime(DateTime date) {
        return date.toDateMidnight();
    }

    public static DateMidnight getLastDayOfMonth(DateMidnight today) {
        return getFirstDayOfMonth(today).plusMonths(1).minusDays(1);
    }

    public static DateMidnight getFirstDayOfQuarter(DateMidnight today) {
        final int i = today.getMonthOfYear() - 1;
        return today.monthOfYear().setCopy(i / 3 * 3 + 1).dayOfMonth().setCopy(1);
    }

    public static DateMidnight getLastDayOfQuarter(DateMidnight today) {
        return getFirstDayOfQuarter(today).plusMonths(3).minusDays(1);
    }

    public static DateMidnight getLastDayOfWeek(DateMidnight date) {
        return date.getDayOfWeek() == SUNDAY ? date : getNextDayInWeek(date, SUNDAY);
    }

    public static DateMidnight getNextDayInWeek(DateMidnight start, int weekday) {
        return start.plusDays(((weekday - start.getDayOfWeek() + 6) % 7) + 1);
    }

    public static DateMidnight getFirstDayOfWeek(DateMidnight date) {
        return getLastDayOfWeek(date).minusDays(6);
    }

    public static DateMidnight toDateMidnight(Date startDate) {
        return startDate != null ? new DateMidnight(startDate.getTime() - startDate.getTimezoneOffset() * 60000) : null;
    }
}
