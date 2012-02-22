package de.atns.common.fileutils;

/**
 * @author tbaum
 * @since 04.01.2010
 */
public interface ZipFileFilter {

    boolean accept(String entryName);
}
