package de.atns.common.gwt.server;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.dao.PartResult;
import de.atns.common.gwt.client.model.ListPresentation;

import java.io.Serializable;

/**
 * @author mwolter
 * @since 26.02.2010 16:15:44
 */
public class ListConverter<F extends Serializable, T extends IsSerializable>
        implements Converter<PartResult<F>, ListPresentation<T>> {
// ------------------------------ FIELDS ------------------------------

    private Converter<F, T> converter;

// -------------------------- STATIC METHODS --------------------------

    public static <F extends Serializable, T extends IsSerializable> ListConverter<F, T> listConverter(final Converter<F, T> converter) {
        return new ListConverter<F, T>(converter);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private ListConverter(final Converter<F, T> converter) {
        this.converter = converter;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------

    @Override
    public ListPresentation<T> convert(final PartResult<F> result) {
        return new ListPresentation<T>(Lambda.convert(result.getItems(), converter),
                result.getStart(), result.getTotal());
    }
}