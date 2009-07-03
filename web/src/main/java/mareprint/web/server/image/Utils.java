package mareprint.web.server.image;

import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayInputStream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @author Michael Hunger
 * @since 01.07.2009
 */
public class Utils {
    public static final float INCH = 2.54f;
    public static final float DOT_MEASURE = 1 / 72.0f;
    public static final int BUFFER_SIZE = 10 * 1024;
    private static final byte[] NULL_BYTES = new byte[0];

    public static float toCentimeter(final float dotValue) {
        return toInch(dotValue) * INCH;
    }
    public static float incToCentimeter(final float inchValue) {
        return inchValue * INCH;
    }

    public static float toInch(final float dotValue) {
        return dotValue * DOT_MEASURE;
    }

    public static String readString(final InputStream is, final int bufferSize) {
        try {
            final byte[] buffer = new byte[bufferSize];
            final int count = is.read(buffer);
            return toString(buffer, count);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static byte[] readBytes(final InputStream is, final int bufferSize) {
        try {
            final byte[] buffer = new byte[bufferSize];
            final int count = is.read(buffer);
            if (count==bufferSize) return buffer;
            final byte[] newBuffer=new byte[count];
            System.arraycopy(buffer,0,newBuffer,0,count);
            return newBuffer;
        } catch (IOException e) {
            e.printStackTrace();
            return NULL_BYTES;
        }
    }


    public static String toString(final byte[] bytes, final int bufferSize) {
        try {
            return new String(bytes, 0, bufferSize, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static float toFloat(final String string) {
        try {
            if (string == null) return 0;
            return Float.valueOf(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String[] extract(final String data, final String regexp, final int... groups) {
        final int groupsCount = groups.length;
        final String[] result = new String[groupsCount > 0 ? groupsCount : 1];
        if (data != null && data.length() > 0) {
            final Pattern pattern = Pattern.compile(regexp);
            final Matcher matcher = pattern.matcher(data);
            if (matcher.find()) {
                if (groupsCount > 0) {
                    for (int i = 0; i < groupsCount; i++) {
                        result[i] = matcher.group(groups[i]);
                    }
                } else {
                    result[0] = matcher.group();
                }
            }
        }
        return result;
    }
}
