package de.atns.common.gwt.datefilter.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import org.gwttime.time.ReadableInstant;

import static org.gwttime.time.DateTimeUtils.getInstantMillis;

/**
 * @author mwolter
 * @since 29.03.12 14:41
 */
public class TimeRange implements IsSerializable {

    private Long start;
    private Long end;

    private TimeRange() {
    }

    public TimeRange(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    public TimeRange(ReadableInstant start, ReadableInstant end) {
        this(start != null ? getInstantMillis(start) : null, end != null ? getInstantMillis(end) : null);
    }

    public Long getEnd() {
        return end;
    }

    public Long getStart() {
        return start;
    }
}
