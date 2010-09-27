package de.atns.printing;

public class Converter {

    public static double convertToMM(final double inches) {
        return inches * 25.4;
    }

    public static double convertToInch(final double mm) {
        return mm / 25.4;
    }

    public static int convertMMToDots(final double x, final int resolution) {
        return (int) (convertToInch(x) * resolution);
    }

}
