package de.atns.common.gwt.server;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.gwt.client.ListPresentation;

/**
 * @author mwolter
 * @since 26.02.2010 16:15:44
 */
public class ListConverter<F extends IsSerializable, T extends IsSerializable> implements Converter<ListPresentation<F>, ListPresentation<T>> {
// ------------------------------ FIELDS ------------------------------

    private Converter<F, T> converter;

// --------------------------- CONSTRUCTORS ---------------------------

    public ListConverter(final Converter<F, T> converter) {
        this.converter = converter;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------

    @Override
    public ListPresentation<T> convert(final ListPresentation<F> result) {
        return new ListPresentation<T>(Lambda.convert(result.getEntries(), converter), result.getStart(), result.getTotal());
    }
}