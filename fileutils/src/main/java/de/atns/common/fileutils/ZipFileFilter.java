package de.atns.common.fileutils;

/**
 * @author tbaum
 * @since 04.01.2010
 */
public interface ZipFileFilter {
// -------------------------- OTHER METHODS --------------------------

    boolean accept(String entryName);
}
