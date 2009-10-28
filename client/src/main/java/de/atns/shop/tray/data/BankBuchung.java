package de.atns.shop.tray.data;

import org.apache.commons.httpclient.NameValuePair;
import org.kapott.hbci.GV_Result.GVRKUms;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author tbaum
 * @since 05.09.2009 17:05:01
 */
public class BankBuchung {
// ------------------------------ FIELDS ------------------------------

    private final long datum;
    private final long wert;
    private final String text;

// --------------------------- CONSTRUCTORS ---------------------------

    public BankBuchung(final GVRKUms.UmsLine line) {
        final StringBuilder text = new StringBuilder(2048);
        for (final String u : line.usage)
            text.append(u).append(' ');

        this.datum = line.bdate.getTime();
        this.wert = line.value.getLongValue();
        this.text = text.toString().replaceAll("\\s+", " ").trim();
    }

    public BankBuchung(final Date datum, final long wert, final String text) {
        this.datum = datum.getTime();
        this.wert = wert;
        this.text = text;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override public String toString() {
        final String d1 = SimpleDateFormat.getDateInstance(DateFormat.SHORT).format(new Date(datum));
        final String d2 = NumberFormat.getCurrencyInstance().format(wert / 100);
        final String d3 = text.substring(0, Math.min(90, text.length()));
        return String.format("%10s\t%15s\t%s", d1, "                                 ".substring(0, Math.max(0, 15 - d2.length())) + d2, d3);
    }

// -------------------------- OTHER METHODS --------------------------

    public void fillRequest(final List<NameValuePair> request) {
        request.add(new NameValuePair("datum", String.valueOf(datum)));
        request.add(new NameValuePair("wert", String.valueOf(wert)));
        request.add(new NameValuePair("text", text));
    }
}
