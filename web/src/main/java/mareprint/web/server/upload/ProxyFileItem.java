package mareprint.web.server.upload;

import mareprint.web.client.model.UploadItemStatus;
import org.apache.commons.fileupload.FileItem;

import java.io.*;

/**
 * @author tbaum
 * @since 29.06.2009 08:20:32
 */
class ProxyFileItem implements FileItem {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 6439086524918276170L;

    private final UploadItemStatus status;
    private final FileItem delegate;

// --------------------------- CONSTRUCTORS ---------------------------

    ProxyFileItem(final UploadItemStatus status, final FileItem delegate) {
        this.status = status;
        this.delegate = delegate;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface FileItem ---------------------

    public InputStream getInputStream() throws IOException {
        return delegate.getInputStream();
    }

    public String getContentType() {
        return delegate.getContentType();
    }

    public String getName() {
        return delegate.getName();
    }

    public boolean isInMemory() {
        return delegate.isInMemory();
    }

    public long getSize() {
        return delegate.getSize();
    }

    public byte[] get() {
        return delegate.get();
    }

    public String getString(final String encoding) throws UnsupportedEncodingException {
        return delegate.getString(encoding);
    }

    public String getString() {
        return delegate.getString();
    }

    public void write(final File file) throws Exception {
        delegate.write(file);
    }

    public void delete() {
        delegate.delete();
    }

    public String getFieldName() {
        return delegate.getFieldName();
    }

    public void setFieldName(final String name) {
        delegate.setFieldName(name);
    }

    public boolean isFormField() {
        return delegate.isFormField();
    }

    public void setFormField(final boolean state) {
        delegate.setFormField(state);
    }

    public OutputStream getOutputStream() throws IOException {
        return new ProxyOutputStream(status, delegate.getOutputStream());
    }

}
