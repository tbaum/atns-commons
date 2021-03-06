package de.atns.common.gwt.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author mwolter
 * @since 11.05.2010 19:02:40
 */
public class IdResult implements Result, IsSerializable {

    private Long id;
    private Long version;
    private Long rubrikId;

    public IdResult() {
    }

    public IdResult(final Long id, final Long version) {
        this.id = id;
        this.version = version;
    }

    public IdResult(final Long id, final Long version, final Long rubrikId) {
        this(id, version);
        this.rubrikId = rubrikId;
    }

    public Long getId() {
        return id;
    }

    public Long getRubrikId() {
        return rubrikId;
    }

    public Long getVersion() {
        return version;
    }

    @Override public String toString() {
        return id + "." + version;
    }
}
