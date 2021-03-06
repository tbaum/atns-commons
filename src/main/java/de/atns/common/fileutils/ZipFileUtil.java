package de.atns.common.fileutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author tbaum
 * @since 04.01.2010
 */
public class ZipFileUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ZipFileUtil.class);

    public static File[] extractZipFile(final ZipFile zipFile, final File targetDir, final ZipFileFilter filter)
            throws IOException {
        final List<File> extractedFiles = new LinkedList<File>();
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry zipEntry = entries.nextElement();
            final String entryName = zipEntry.getName();
            if (filter.accept(entryName)) {
                LOG.debug("extracting " + entryName + " to " + targetDir);
                final File extractFile = new File(targetDir, entryName);
                final File tempFile = new File(targetDir, entryName + ".tmp");
                tempFile.deleteOnExit();

                FileUtil.copy(zipFile.getInputStream(zipFile.getEntry(entryName)), new FileOutputStream(tempFile));
                if (!tempFile.renameTo(extractFile)) {
                    throw new IOException("Error renaming temp file " + tempFile + " to " + extractFile);
                }

                extractedFiles.add(extractFile);
            }
        }
        return extractedFiles.toArray(new File[extractedFiles.size()]);
    }
}
