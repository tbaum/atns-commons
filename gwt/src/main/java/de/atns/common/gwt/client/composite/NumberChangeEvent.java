package de.atns.common.gwt.client.composite;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author tbaum
 * @since 08.09.2010
 */
class NumberChangeEvent extends ValueChangeEvent<Number> {

    public static <S extends HasValueChangeHandlers<Number> & HasHandlers> void fireIfNotEqualNumbers(
            final S source, final Number oldValue, final Number newValue) {
        if (ValueChangeEvent.shouldFire(source, oldValue, newValue)) {
            source.fireEvent(new NumberChangeEvent(newValue));
        }
    }

    protected NumberChangeEvent(final Number value) {
        super(value);
    }
}
