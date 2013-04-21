package de.atns.common.gwt.datefilter.client;

import org.gwttime.time.DateMidnight;
import org.gwttime.time.Interval;

import java.io.Serializable;

import static de.atns.common.gwt.datefilter.client.DateUtils.*;

/**
 * @author mwolter
 * @since 16.03.12 09:19
 */
public enum TimeSearch {

    LAST_WEEK(new CreateSpan() {

        @Override public Interval create() {
            DateMidnight start = getFirstDayOfWeek(new DateMidnight()).minusDays(7);
            return new Interval(getFirstDayOfWeek(start), getLastDayOfWeek(start));
        }
    }),
    LAST_MONTH(new CreateSpan() {

        @Override public Interval create() {
            DateMidnight start = getFirstDayOfMonth(new DateMidnight()).minusMonths(1);
            return new Interval(getFirstDayOfMonth(start), getLastDayOfMonth(start));
        }
    }),
    LAST_QUARTER(new CreateSpan() {

        @Override public Interval create() {
            DateMidnight start = getFirstDayOfMonth(new DateMidnight()).minusMonths(3);
            return new Interval(getFirstDayOfQuarter(start), getLastDayOfQuarter(start));
        }
    }),
    CURRENT_WEEK(new CreateSpan() {

        @Override public Interval create() {
            DateMidnight today = new DateMidnight();
            return new Interval(getFirstDayOfWeek(today), getLastDayOfWeek(today));
        }
    }),
    CURRENT_MONTH(new CreateSpan() {

        @Override public Interval create() {
            DateMidnight today = new DateMidnight();
            return new Interval(getFirstDayOfMonth(today), getLastDayOfMonth(today));
        }
    }),
    CURRENT_QUARTER(new CreateSpan() {

        @Override public Interval create() {
            DateMidnight today = new DateMidnight();
            return new Interval(getFirstDayOfQuarter(today), getLastDayOfQuarter(today));
        }
    });
    private final CreateSpan createSpan;

    TimeSearch(CreateSpan createSpan) {
        this.createSpan = createSpan;
    }

    public Interval getDates() {
        return createSpan.create();
    }

    private interface CreateSpan extends Serializable {

        Interval create();
    }
}
