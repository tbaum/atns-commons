package de.atns.common;

import java.io.Serializable;

public class Tuple<A, B> implements Serializable {

    public final A a;
    public final B b;

    public static <A, B> Tuple<A, B> tuple(final A a, final B b) {
        return new Tuple<A, B>(a, b);
    }

    protected Tuple(final A a, final B b) {
        this.a = a;
        this.b = b;
    }

    public A getKey() {
        return a;
    }

    public B getValue() {
        return b;
    }
}

