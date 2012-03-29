package de.atns.common.gwt.datefilter.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import de.atns.common.gwt.client.Util;
import org.gwttime.time.DateMidnight;
import org.gwttime.time.Interval;

import java.util.Date;

import static de.atns.common.gwt.datefilter.client.TimeSearch.*;

/**
 * @author mwolter
 * @since 04.08.2010 17:35:53
 */
public class DateFilterView extends Widget implements HasValueChangeHandlers<TimeRange> {

    private static final DateTimeFormat timeFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
    private final DateBox startDateBox = new DateBox(new DatePicker(), null, new DateBox.DefaultFormat(timeFormat));
    private final DateBox finishDateBox = new DateBox(new DatePicker(), null, new DateBox.DefaultFormat(timeFormat));
    private final ListBox rangeBox = new ListBox();

    public DateFilterView() {
        rangeBox.addItem("Gesamter Zeitraum", "");
        rangeBox.addItem("Aktuelle Woche", CURRENT_WEEK.name());
        rangeBox.addItem("Aktueller Monat", CURRENT_MONTH.name());
        rangeBox.addItem("Aktuelles Quartal", CURRENT_QUARTER.name());
        rangeBox.addItem("Letzte Woche", LAST_WEEK.name());
        rangeBox.addItem("Letzter Monat", LAST_MONTH.name());
        rangeBox.addItem("Letztes Quartal", LAST_QUARTER.name());

        rangeBox.addChangeHandler(new ChangeHandler() {

            @Override public void onChange(ChangeEvent event) {
                TimeSearch format = Util.parseEnum(rangeBox, TimeSearch.class);
                if (format == null) {
                    startDateBox.setValue(null);
                    finishDateBox.setValue(null);
                } else {
                    final Interval range = format.getDates();
                    startDateBox.setValue(range.getStart().toDate());
                    finishDateBox.setValue(range.getEnd().toDate());
                }
                fireEvent(new ValueChangeEvent<TimeRange>(getRange()) {
                });
            }
        });

        final ValueChangeHandler<Date> changeHandler = new ValueChangeHandler<Date>() {

            @Override public void onValueChange(ValueChangeEvent<Date> dateValueChangeEvent) {
                rangeBox.setSelectedIndex(0);
                fireEvent(new ValueChangeEvent<TimeRange>(getRange()) {
                });
            }
        };
        startDateBox.addValueChangeHandler(changeHandler);
        finishDateBox.addValueChangeHandler(changeHandler);
    }

    public TimeRange getRange() {
        final Date startDate = startDateBox.getValue();
        final Date finshDate = finishDateBox.getValue();

        return new TimeRange(
                startDate == null ? null : new DateMidnight(startDate),
                finshDate == null ? null : new DateMidnight(finshDate));
    }

    public DateBox getFinishDateBox() {
        return finishDateBox;
    }

    public ListBox getRangeBox() {
        return rangeBox;
    }

    public DateBox getStartDateBox() {
        return startDateBox;
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<TimeRange> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    public void reset() {
        rangeBox.setSelectedIndex(0);
        startDateBox.setValue(null);
        finishDateBox.setValue(null);
    }
}
