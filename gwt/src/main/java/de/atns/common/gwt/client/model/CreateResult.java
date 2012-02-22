package de.atns.common.gwt.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author tbaum
 * @since 16.02.2010
 */
public class CreateResult implements Result, IsSerializable {

    private long id;

    protected CreateResult() {
    }

    public CreateResult(final long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
