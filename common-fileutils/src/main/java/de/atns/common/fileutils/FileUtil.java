package de.atns.common.fileutils;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

public abstract class FileUtil {
// -------------------------- STATIC METHODS --------------------------

    public static void setupDir(final File dir) {
        if (dir.exists()) {
            for (final File file : findFiles(dir, ".*")) {
                file.delete();
            }
        } else dir.mkdirs();
    }

    public static File[] findFiles(final File dir, final String fileRegexp) {
        return dir.listFiles(new FileFilter() {
            public boolean accept(final File file) {
                return file.getName().matches(fileRegexp);
            }
        });
    }

    public static Collection<String> getFileNames(final File[] files) {
        final Collection<String> fileNames = new HashSet<String>();
        for (final File file : files) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }

    public static byte[] readFile(final File file) throws IOException {
        final ByteArrayOutputStream p = new ByteArrayOutputStream();
        final FileInputStream fis = new FileInputStream(file);
        int rd;
        final byte[] buf = new byte[4096];
        while ((rd = fis.read(buf)) > 0) p.write(buf, 0, rd);

        return p.toByteArray();
    }

    public static Properties loadProperties(final File propertyFile) {
        try {
            final Properties properties = new Properties();
            properties.load(new FileReader(propertyFile));
            return properties;
        } catch (IOException ex) {
            throw new RuntimeException("Unable to Load Properties from " + propertyFile, ex);
        }
    }
}