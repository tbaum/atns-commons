package de.atns.common;

import java.util.Iterator;
import java.util.Map;

public class TupleIterator<A, B> implements Iterator<Tuple<A, B>> {
// ------------------------------ FIELDS ------------------------------

    private final Iterator<Map.Entry<A, B>> entryIterator;

// -------------------------- STATIC METHODS --------------------------

    public static <A, B> TupleIterator<A, B> iterator(final Iterator<Map.Entry<A, B>> entryIterator) {
        return new TupleIterator<A, B>(entryIterator);
    }

    public static <A, B> TupleIterator<A, B> readonylIterator(final Iterator<Map.Entry<A, B>> entryIterator) {
        return new TupleIterator<A, B>(entryIterator) {
            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove");
            }
        };
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private TupleIterator(final Iterator<Map.Entry<A, B>> entryIterator) {
        this.entryIterator = entryIterator;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Iterator ---------------------

    @Override
    public boolean hasNext() {
        return entryIterator.hasNext();
    }

    @Override
    public Tuple<A, B> next() {
        final Map.Entry<A, B> entry = entryIterator.next();
        return Tuple.tuple(entry.getKey(), entry.getValue());
    }

    @Override
    public void remove() {
        entryIterator.remove();
    }
}
