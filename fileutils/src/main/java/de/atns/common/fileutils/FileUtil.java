package de.atns.common.fileutils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.Properties;

public abstract class FileUtil {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(FileUtil.class);
    private static final int BUFFER_SIZE = 1024 * 10;

// -------------------------- STATIC METHODS --------------------------

    public static void removeAndCreateDir(final File dir) {
        if (dir.exists()) {
            for (final File file : findFiles(dir, ".*")) {
                file.delete();
            }
        } else {
            dir.mkdirs();
        }
    }

    public static File[] findFiles(final File dir, final String fileRegexp) {
        final File[] files = dir.listFiles(new FileFilter() {
            @Override public boolean accept(final File file) {
                return file.getName().matches(fileRegexp);
            }
        });
        if (files == null) {
            LOG.error("unable to read '" + dir + "'");
            return new File[0];
        }
        return files;
    }

    public static byte[] readFile(final File file) throws IOException {
        final ByteArrayOutputStream p = new ByteArrayOutputStream();
        final FileInputStream fis = new FileInputStream(file);
        int rd;
        final byte[] buf = new byte[4096];
        while ((rd = fis.read(buf)) > 0) {
            p.write(buf, 0, rd);
        }

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

    public static String getPathFromUuid(final int c, final String uuid) {
        final String cleanUuid = uuid.replaceAll("[^0-9a-z-]", "");
        final String cleanUuid2 = uuid.replaceAll("[^0-9a-z]", "");
        final StringBuffer path = new StringBuffer();
        for (int i = 0; i < c; i++) {
            path.append(cleanUuid2.charAt(i)).append('/');
        }
        path.append(cleanUuid);
        return path.toString();
    }

    public static void copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(inputStream, BUFFER_SIZE);
            bufferedOutputStream = new BufferedOutputStream(outputStream, BUFFER_SIZE);
            final byte[] buffer = new byte[BUFFER_SIZE];
            int count;
            while ((count = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, count);
            }
        } finally {
            closeSilent(bufferedOutputStream);
            closeSilent(bufferedInputStream);
        }
    }

    public static void closeSilent(final Closeable closeable) {
        closeSilent(closeable, null);
    }

    public static void closeSilent(final Socket closeable) {
        closeSilent(closeable, null);
    }

    public static void closeSilent(final Closeable closeable, final String message) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                if (message != null) {
                    LOG.error(MessageFormat.format(message, e.getMessage()));
                }
            }
        }
    }

    public static void closeSilent(final Socket closeable, final String message) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                if (message != null) {
                    LOG.error(MessageFormat.format(message, e.getMessage()));
                }
            }
        }
    }

    public static File extractResource(File targetDir, String resourceName, Class sourceClass) throws IOException {
        File file = new File(targetDir, resourceName);
        if (!file.exists()) {
            System.out.println("creating " + file);
            file.getParentFile().mkdirs();
            InputStream is = sourceClass.getResourceAsStream(resourceName);
            FileOutputStream fo = new FileOutputStream(file);
            byte[] buffer = new byte[2048];
            int i;
            while ((i = is.read(buffer)) > 0) {
                fo.write(buffer, 0, i);
            }
            fo.close();
        }
        return file;
    }
}
