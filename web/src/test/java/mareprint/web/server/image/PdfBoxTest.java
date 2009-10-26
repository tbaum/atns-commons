package mareprint.web.server.image;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static mareprint.web.server.image.Utils.readString;

/**
 * @author Michael Hunger
 * @since 01.07.2009
 */
public class PdfBoxTest {
    private static final int EXPECTED_WIDTH = 10;
    private static final int BUFFER_SIZE = EXPECTED_WIDTH * 1024;
    private static final float DELTA = .1f;
    private static final int EXPECTED_HEIGHT = 20;
    private static final float WIDTH_2 = 22.8f;
    private static final float HEIGTH_2 = 18.1f;

    @Test
    public void testGrepSize() throws IOException {
        final byte[] bytes = Utils.readBytes(getStream("test.pdf"), BUFFER_SIZE);
        checkSize(new PdfDimensionParser(bytes), EXPECTED_WIDTH, EXPECTED_HEIGHT);
    }

    @Test
    public void testGrepSize2() throws IOException {
        final InputStream pdfStream = getStream("test2.pdf");
        checkSize(pdfStream, WIDTH_2, HEIGTH_2);
    }

    @Test
    public void testGetSize() throws IOException {
        final InputStream pdfStream = getStream("test.pdf");
        checkSize(pdfStream, EXPECTED_WIDTH, EXPECTED_HEIGHT);
    }

    @Test
    public void testGetSizePartial() throws IOException {
        final InputStream pdfStream = getStream("test.pdf");
        final byte[] buffer = new byte[BUFFER_SIZE];
        final int count = pdfStream.read(buffer, 0, buffer.length);
        assertEquals(BUFFER_SIZE, count);
        final ByteArrayInputStream partialStream = new ByteArrayInputStream(buffer);

        checkSize(partialStream, EXPECTED_WIDTH, EXPECTED_HEIGHT);
    }

    @Test
    public void testGetSize2() throws IOException {
        final InputStream pdfStream = getStream("test2.pdf");
        checkSize(pdfStream, WIDTH_2, HEIGTH_2);
    }

    //  /MediaBox [0 0 283.4646 566.9291]
    @Test
    public void testGrep() throws IOException {
        final String data = readString(getStream("test.pdf"), BUFFER_SIZE);
        final Pattern pattern = Pattern.compile("/MediaBox\\s*\\[([\\d.]+) ([\\d.]+) ([\\d.]+) ([\\d.]+)\\]");
        final Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            final String widthString = matcher.group(3);
            final String heightString = matcher.group(4);
            System.out.println("widthString = " + widthString);
            assertEquals("283.4646", widthString);
            System.out.println("heightString = " + heightString);
            assertEquals("566.9291", heightString);
        }
    }


    private InputStream getStream(String fileName) {
        return getClass().getResourceAsStream(fileName);
    }

    @Test
    public void testGetSizeFromPartial() throws IOException {
        final InputStream pdfStream = getStream("test.pdf");
        final byte[] buffer = new byte[BUFFER_SIZE];
        final int count = pdfStream.read(buffer, 0, buffer.length);
        assertEquals(BUFFER_SIZE, count);
        final ImageParser extractor = new PdfDimensionParser(buffer);
        checkSize(extractor, EXPECTED_WIDTH, EXPECTED_HEIGHT);
    }

    private void checkSize(ImageParser parser, final float expectedWidth, final float expectedHeight) {
        assertEquals(expectedWidth, parser.getWidthCentimeter(), DELTA);
        assertEquals(expectedHeight, parser.getHeightCentimeter(), DELTA);
    }

    private void checkSize(final InputStream pdfStream, final float expectedWidth, final float expectedHeight) throws IOException {
        final ImageParser parser = new PdfDimensionParser(pdfStream);
        final double widthInCentimeter = parser.getWidthCentimeter();
        final double heightInCentimeter = parser.getHeightCentimeter();
        assertEquals(expectedWidth, widthInCentimeter, DELTA);
        assertEquals(expectedHeight, heightInCentimeter, DELTA);
    }
}
