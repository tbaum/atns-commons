package de.atns.common.gwt.datefilter.client;

import net.customware.gwt.dispatch.shared.Result;
import org.gwttime.time.DateMidnight;

/**
 * @author mwolter
 * @since 16.03.12 09:37
 */
public class TimeSpan implements Result {

    private DateMidnight start;
    private DateMidnight end;

    private TimeSpan() {
    }

    public TimeSpan(DateMidnight start, DateMidnight end) {
        this.start = start;
        this.end = end;
    }

    public DateMidnight getEnd() {
        return end;
    }

    public DateMidnight getStart() {
        return start;
    }
}
