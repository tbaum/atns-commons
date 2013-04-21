package de.atns.common.fileutils;

/**
 * @author tbaum
 * @since 25.08.11
 */
public class SingleFileFilter implements ZipFileFilter {

    private final String dateiName;

    public SingleFileFilter(String dateiName) {
        this.dateiName = dateiName;
    }

    @Override public boolean accept(final String entryName) {
        return entryName.equals(dateiName);
    }
}
