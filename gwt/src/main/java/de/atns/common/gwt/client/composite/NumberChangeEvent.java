package de.atns.common.gwt.client.composite;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author tbaum
 * @since 08.09.2010
 */
class NumberChangeEvent extends ValueChangeEvent<Number> {
// -------------------------- STATIC METHODS --------------------------

    public static <S extends HasValueChangeHandlers<Number> & HasHandlers> void fireIfNotEqualNumbers(
            S source, Number oldValue, Number newValue) {
        if (ValueChangeEvent.shouldFire(source, oldValue, newValue)) {
            source.fireEvent(new NumberChangeEvent(newValue));
        }
    }

// --------------------------- CONSTRUCTORS ---------------------------

    protected NumberChangeEvent(Number value) {
        super(value);
    }
}
