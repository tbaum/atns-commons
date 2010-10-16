package de.atns.common;

import java.io.Serializable;

public class Tuple<A, B> implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -911474477229575566L;
    public final A a;
    public final B b;

// -------------------------- STATIC METHODS --------------------------

    public static <A, B> Tuple<A, B> tuple(final A a, final B b) {
        return new Tuple<A, B>(a, b);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    protected Tuple(final A a, final B b) {
        this.a = a;
        this.b = b;
    }

// -------------------------- OTHER METHODS --------------------------

    public A getKey() {
        return a;
    }

    public B getValue() {
        return b;
    }
}

