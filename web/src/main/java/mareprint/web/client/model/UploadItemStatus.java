package mareprint.web.client.model;

import java.io.Serializable;

/**
 * @author tbaum
 * @since 29.06.2009 08:36:19
 */
public class UploadItemStatus implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -1042362456915366594L;
    private final String fileName;
    private final String contentType;
    private ImageInfo imageInfo;

// --------------------------- CONSTRUCTORS ---------------------------

    public UploadItemStatus(final String fileName, final String contentType) {
        this.fileName = fileName;
        this.contentType = contentType;
        System.err.println(this);
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public boolean matches(String name) {
        return this.fileName!=null && name!=null && fileName.equalsIgnoreCase(name);
    }

    public String toString() {
        return ""+fileName+" = "+contentType+" ("+imageInfo+")";
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean hasExtension(String ext) {
        return getFileName()!=null && getFileName().endsWith(ext);
    }

    public boolean isOctetStream() {
        return getContentType().equals("application/octet-stream");
    }
}
