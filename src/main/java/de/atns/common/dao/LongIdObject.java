package de.atns.common.dao;

import ch.lambdaj.function.convert.Converter;

import java.io.Serializable;
import java.util.Date;

public interface LongIdObject extends Serializable {

    Converter<LongIdObject, Long> CONVERTER = new Converter<LongIdObject, Long>() {
        @Override public Long convert(final LongIdObject from) {
            return from.getId();
        }
    };

    Date getCreateTimestamp();

    Long getId();

    Date getLastUpdateTimestamp();

    boolean isNew();

    void setId(final Long id);
}
