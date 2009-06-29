package mareprint.web.server.upload;

import mareprint.web.client.model.ServerUploadStatus;
import mareprint.web.client.model.UploadItemStatus;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;

/**
 * @author tbaum
 * @since 29.06.2009 08:20:38
 */
class ProxyFileItemFactory implements FileItemFactory {
// ------------------------------ FIELDS ------------------------------

    private final ServerUploadStatus uploadStatus;
    private FileItemFactory delegate;

// --------------------------- CONSTRUCTORS ---------------------------

    public ProxyFileItemFactory(final ServerUploadStatus uploadStatus, final FileItemFactory delegate) {
        this.uploadStatus = uploadStatus;
        this.delegate = delegate;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface FileItemFactory ---------------------

    public FileItem createItem(final String fieldName, final String contentType, final boolean isFormField, final String fileName) {
        final FileItem fileItem = delegate.createItem(fieldName, contentType, isFormField, fileName);
        final UploadItemStatus status = uploadStatus.createItem(fileName, contentType);
        return new ProxyFileItem(status, fileItem);
    }
}
