package mareprint.web.server.upload;

import mareprint.web.client.model.UploadItemStatus;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author tbaum
 * @since 29.06.2009 08:34:19
 */
public class ProxyOutputStream extends OutputStream {
// ------------------------------ FIELDS ------------------------------

    private final OutputStream delegate;

// --------------------------- CONSTRUCTORS ---------------------------

    public ProxyOutputStream(final UploadItemStatus status, final OutputStream outputStream) {
        delegate = outputStream;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Closeable ---------------------


    public void close() throws IOException {
        delegate.close();
    }

// --------------------- Interface Flushable ---------------------

    public void flush() throws IOException {
        delegate.flush();
    }

// -------------------------- OTHER METHODS --------------------------

    public void write(final int b) throws IOException {
        delegate.write(b);
    }

    public void write(final byte[] b) throws IOException {
        delegate.write(b);
    }

    public void write(final byte[] b, final int off, final int len) throws IOException {
        delegate.write(b, off, len);
    }
}
