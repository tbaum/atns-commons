package de.atns.common.util;

import java.math.BigDecimal;
import java.util.Map;

import static java.math.BigDecimal.*;

/**
 * @author tbaum
 * @since 14.07.2009 10:44:08
 */
public class NumberUtils {

    public static String formatNumber(final BigDecimal number, final int fractions) {
        return number.setScale(fractions, ROUND_HALF_UP).toString();
    }

    public static BigDecimal max(final BigDecimal a, final BigDecimal b, final int scale) {
        if (a == null) return b;
        if (b == null) return a;
        return scaledCompare(a, b, scale) > 0 ? a : b;
    }

    public static int scaledCompare(final BigDecimal b1, final BigDecimal b2, final int scale) {
        return fixScale(b1, scale).compareTo(fixScale(b2, scale));
    }

    public static BigDecimal fixScale(final BigDecimal bigDecimal, final int scale) {
        return (bigDecimal == null ? ZERO : bigDecimal).setScale(scale, ROUND_HALF_UP);
    }

    public static int scaledCompare(final BigDecimal b1, final BigDecimal b2) {
        return scaledCompare(b1, b2, b1.scale());
    }

    public static BigDecimal fixScale(final double value, final int scale) {
        return valueOf(value).setScale(scale, ROUND_HALF_UP);
    }

    public static BigDecimal min(final BigDecimal a, final BigDecimal b, final int scale) {
        if (a == null) return b;
        if (b == null) return a;
        return scaledCompare(a, b, scale) < 0 ? a : b;
    }

    public static boolean scaledEquals(final BigDecimal b1, final BigDecimal b2) {
        return scaledEquals(b1, b2, b1.scale());
    }

    public static boolean scaledEquals(final BigDecimal b1, final BigDecimal b2, final int scale) {
        return fixScale(b1, scale).equals(fixScale(b2, scale));
    }

    public static BigDecimal netto(final BigDecimal brutto, final BigDecimal mwst) {
        final BigDecimal mwst1 = mwst.divide(valueOf(100), 2, ROUND_HALF_UP).add(ONE);
        return fixScale(brutto.divide(mwst1, 2, ROUND_HALF_UP), 2);
    }

    public static BigDecimal mwst(final BigDecimal brutto, final BigDecimal mwst) {
        final BigDecimal mwst1 = fixScale(mwst, 0);
        final BigDecimal mwst100 = mwst1.add(valueOf(100));
        final BigDecimal mwstBetrag = fixScale(brutto, 2).multiply(mwst1).divide(mwst100, 2, ROUND_HALF_UP);
        return fixScale(mwstBetrag, 2);
    }

    public static void addValue(final Map<BigDecimal, BigDecimal> map, final BigDecimal key, final BigDecimal value) {
        BigDecimal bisher = map.get(key);
        if (bisher == null) {
            bisher = ZERO;
        }
        bisher = bisher.add(value);
        map.put(key, bisher);
    }
}
