package de.atns.common.fileutils;

/**
 * @author tbaum
 * @since 25.08.11
 */
public class SingleFileFilter implements ZipFileFilter {
// ------------------------------ FIELDS ------------------------------

    private final String dateiName;

// --------------------------- CONSTRUCTORS ---------------------------

    public SingleFileFilter(String dateiName) {
        this.dateiName = dateiName;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ZipFileFilter ---------------------

    @Override public boolean accept(final String entryName) {
        return entryName.equals(dateiName);
    }
}
