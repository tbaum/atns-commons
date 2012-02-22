package de.atns.common.crud.client.sortheader;

public interface OrderField {

    String name();

    enum Sort {
        NONE, DESC, ASC;

        public Sort next() {
            return values()[(ordinal() + 1) % values().length];
        }
    }
}
