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

// --------------------------- CONSTRUCTORS ---------------------------

    public UploadItemStatus(final String fileName, final String contentType) {
        this.fileName = fileName;
        this.contentType = contentType;
    }
}
