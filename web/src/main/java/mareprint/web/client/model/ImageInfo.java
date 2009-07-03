package mareprint.web.client.model;

import java.io.Serializable;

/**
 * @author Michael Hunger
 * @since 30.06.2009
 */
public class ImageInfo implements Serializable {
    String formatName;
    String mimeType;
    float width;
    float height;
    float widthCm;
    float heightCm;
    int bitPerPixel;

    public ImageInfo(String formatName, String mimeType) {
        this.formatName = formatName;
        this.mimeType = mimeType;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setPhysicalSize(float widthCm, float heightCm) {
        this.widthCm = widthCm;
        this.heightCm = heightCm;
    }

    public void setBitPerPixel(int bitPerPixel) {
        this.bitPerPixel = bitPerPixel;
    }

    @Override
    public String toString() {
        return formatName+" "+width+"x"+height+" "+ widthCm +"x"+ heightCm +" cm "+bitPerPixel+" bpp";
    }
}
