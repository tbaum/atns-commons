package net.customware.gwt.dispatch.shared.general;

import net.customware.gwt.dispatch.shared.AbstractSimpleResult;

public class BooleanResult extends AbstractSimpleResult<Boolean> {

    private static final long serialVersionUID = 1L;

    protected BooleanResult() {
    }

    public BooleanResult(Boolean value) {
        super(value);
    }
}
