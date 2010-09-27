package de.atns.printing.environment;

import de.atns.printing.document.Mode;

public enum Material {
    NONE(0, 0, Mode.TT, "NONE"),
    TD_21792(102, 152, Mode.TD, "TD_21792"),
    TD_21786(76, 51, Mode.TD, "TD_21786"),
    TD_23601(102, 38, Mode.TD, "TD_23601"),
    THT_15777(70, 31, Mode.TT, "THT_15777"),
    THT_25609(105, 74, Mode.TT, "THT_25609"),
    THT_15776(51, 26, Mode.TT, "THT_15776");

// ------------------------------ FIELDS ------------------------------

    private final int height;
    private final Mode mode;
    private final String name;
    private final int width;

// --------------------------- CONSTRUCTORS ---------------------------

    private Material(final int width, final int height, final Mode mode, final String name) {
        this.width = width;
        this.height = height;
        this.mode = mode;
        this.name = name;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getHeight() {
        return this.height;
    }

    public Mode getMode() {
        return this.mode;
    }

    public String getName() {
        return this.name;
    }

    public int getWidth() {
        return this.width;
    }
}
