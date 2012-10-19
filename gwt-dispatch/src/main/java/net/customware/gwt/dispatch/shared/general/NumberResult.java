package net.customware.gwt.dispatch.shared.general;

import net.customware.gwt.dispatch.shared.AbstractSimpleResult;

public class NumberResult extends AbstractSimpleResult<Number> {

    private static final long serialVersionUID = 1L;

    protected NumberResult() {
    }

    public NumberResult(Number value) {
        super(value);
    }
}
