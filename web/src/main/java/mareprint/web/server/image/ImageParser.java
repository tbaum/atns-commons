package mareprint.web.server.image;

import mareprint.web.client.model.UploadItemStatus;
import mareprint.web.client.model.ImageInfo;

import java.io.InputStream;

/**
 * @author Michael Hunger
 * @since 01.07.2009
 */
public interface ImageParser {
    int getBitsPerPixel();

    String getFormatName();

    String getMimeType();

    float getHeightCentimeter();

    float getWidthCentimeter();

    float getWidth();

    float getHeight();

    public class Do {
        public static void parse(final InputStream stream, final UploadItemStatus status) {
            final ImageParser parser = createParser(stream, status);
            if (parser!=null) {
                final ImageInfo imageInfo = createImageInfo(parser);
                status.setImageInfo(imageInfo);
            }
        }

        private static ImageParser createParser(InputStream stream, UploadItemStatus itemStatus) {
            if (itemStatus.isOctetStream() && itemStatus.hasExtension("pdf")) return new PdfDimensionParser(stream);
            final BitmapImageParser bitmapImageParser = new BitmapImageParser();
            bitmapImageParser.setInput(stream);
            if (bitmapImageParser.check()) return bitmapImageParser;
            return null;
        }

        private static ImageInfo createImageInfo(final ImageParser parser) {
            final ImageInfo imageInfo = new ImageInfo(parser.getFormatName(), parser.getMimeType());
            imageInfo.setSize(parser.getWidth(), parser.getHeight());
            imageInfo.setPhysicalSize(parser.getWidthCentimeter(), parser.getHeightCentimeter());
            imageInfo.setBitPerPixel(parser.getBitsPerPixel());
            return imageInfo;
        }

    }
}
