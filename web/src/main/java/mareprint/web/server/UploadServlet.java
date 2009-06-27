package mareprint.web.server;

import mareprint.web.client.model.ServerUploadStatus;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import static org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import java.util.List;

/**
 * @author tbaum
 * @since 27.06.2009 03:08:28
 */
public class UploadServlet extends HttpServlet {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -7889501923399937L;

// -------------------------- OTHER METHODS --------------------------

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (!isMultipartContent(request)) {
            throw new ServletException("invalid request");
        }
        ServerUploadStatus status = getServerUploadStatus(request);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(500 * 1024 * 1024);

        upload.setProgressListener(new ProgressListener() {
            private long lastCall = -1;

            public void update(long read, long length, int item) {
                if (1000 + lastCall <= currentTimeMillis()) {
                    lastCall = currentTimeMillis();
                    System.err.println(format("item %d %dMB/%dMB %d%%", item, read / 1000, length / 1000, 100 * read / length));

                }
            }
        });

        try {
            List<FileItem> items = upload.parseRequest(request);
            System.err.println("got items");
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();

                    System.err.println(format("'%s'='%s'\n", name, value));
                } else {
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    String contentType = item.getContentType();
                    boolean isInMemory = item.isInMemory();
                    long sizeInBytes = item.getSize();

                    System.err.println(format("'%s'='%s' ct='%s' mem=%s size=%d", fieldName, fileName, contentType, isInMemory, sizeInBytes));

                    File uploadedFile = File.createTempFile("upload", "bin");
                    item.write(uploadedFile);
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static ServerUploadStatus getServerUploadStatus(final HttpServletRequest request) {
        HttpSession session = request.getSession();

        ServerUploadStatus uploadStatus = (ServerUploadStatus) session.getAttribute(ServerUploadStatus.SESSION_ATTR_NAME);
        if (uploadStatus == null) {
            uploadStatus = new ServerUploadStatus();
            session.setAttribute(ServerUploadStatus.SESSION_ATTR_NAME, uploadStatus);
        }
        return uploadStatus;
    }
}
