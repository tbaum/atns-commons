package mareprint.web.server.upload;

import mareprint.web.client.model.UploadItemStatus;
import mareprint.web.client.model.ImageInfo;
import mareprint.web.server.image.BitmapImageParser;
import mareprint.web.server.image.ImageParser;

import java.io.*;

/**
 * @author tbaum
 * @since 29.06.2009 08:34:19
 */
public class ProxyOutputStream extends OutputStream {
// ------------------------------ FIELDS ------------------------------

    private final UploadItemStatus status;
    private final OutputStream delegate;
    private ByteArrayOutputStream buffer=new ByteArrayOutputStream(BUFFER_SIZE);
    private static final int BUFFER_SIZE = 1024*50;

// --------------------------- CONSTRUCTORS ---------------------------

    public ProxyOutputStream(final UploadItemStatus status, final OutputStream outputStream) {
        this.status = status;
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
        if (buffer!=null) {
            buffer.write(b);
            checkData();
        }
    }

    private void checkData() {
        if (buffer.size()>=BUFFER_SIZE) {
            final InputStream bis = new ByteArrayInputStream(buffer.toByteArray());
            buffer=null;
            ImageParser.Do.parse(bis, status);
        }
    }


    public void write(final byte[] b) throws IOException {
        delegate.write(b);
        if (buffer!=null) {
            buffer.write(b);
            checkData();
        }
    }

    public void write(final byte[] b, final int off, final int len) throws IOException {
        delegate.write(b, off, len);
        if (buffer!=null) {
            buffer.write(b);
            checkData();
        }
    }
}
