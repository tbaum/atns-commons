package mareprint.web.server.image;

import static mareprint.web.server.image.Utils.toFloat;
import static mareprint.web.server.image.Utils.toInch;
import static mareprint.web.server.image.Utils.toCentimeter;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * @author Michael Hunger
 * @since 01.07.2009
 */
public class PdfDimensionParser implements ImageParser {
    private float width;
    private float height;
    private static final String REGEXP = "/MediaBox\\s*\\[([\\d.]+) ([\\d.]+) ([\\d.]+) ([\\d.]+)\\]";

    public PdfDimensionParser(final byte[] bytes) {
        final String[] dimensions = Utils.extract(Utils.toString(bytes, bytes.length), REGEXP, 3, 4);
        this.width = toFloat(dimensions[0]);
        this.height = toFloat(dimensions[1]);
    }

    public PdfDimensionParser(InputStream stream) {
        this(Utils.readBytes(stream, Utils.BUFFER_SIZE));
    }

    public int getBitsPerPixel() {
        return 0;
    }

    public String getFormatName() {
        return "PDF";
    }

    public String getMimeType() {
        return "application/pdf";
    }

    public float getHeightCentimeter() {
        return toCentimeter(getHeight());
    }

    public float getWidthCentimeter() {
        return toCentimeter(getWidth());
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
