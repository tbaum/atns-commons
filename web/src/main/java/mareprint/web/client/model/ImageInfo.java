package mareprint.web.client.model;

import java.io.Serializable;

/**
 * @author Michael Hunger
 * @since 30.06.2009
 */
public class ImageInfo implements Serializable {
    String formatName;
    String mimeType;
    int width;
    int height;
    float widthInch;
    float heightInch;
    int bitPerPixel;

    public ImageInfo(String formatName, String mimeType) {
        this.formatName = formatName;
        this.mimeType = mimeType;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setPhysicalSize(float widthInch, float heightInch) {
        this.widthInch = widthInch;
        this.heightInch = heightInch;
    }

    public void setBitPerPixel(int bitPerPixel) {
        this.bitPerPixel = bitPerPixel;
    }

    @Override
    public String toString() {
        return formatName+" "+width+"x"+height+" "+widthInch+"x"+heightInch+" \" "+bitPerPixel+" bpp";
    }
}
