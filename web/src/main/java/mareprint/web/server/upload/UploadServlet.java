package mareprint.web.server.upload;

import mareprint.web.client.model.ServerUploadStatus;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import static org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.String.format;
import java.nio.channels.FileChannel;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        final ProxyFileItemFactory fileItemFactory = new ProxyFileItemFactory(status, new DiskFileItemFactory());

        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        upload.setSizeMax(500 * 1024 * 1024);

        try {
            List<FileItem> items = upload.parseRequest(request);

            PrintWriter out = response.getWriter();

            boolean gotFile = false;

            for (FileItem item : items) {
                if (!item.isFormField()) {

                    if (gotFile)
                        throw new ServletException("multiple files received");

                    gotFile = true;
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    String contentType = item.getContentType();
                    boolean isInMemory = item.isInMemory();
                    long sizeInBytes = item.getSize();

                    System.err.println(format("'%s'='%s' ct='%s' mem=%s size=%d", fieldName, fileName, contentType, isInMemory, sizeInBytes));

                    File uploadedFile = File.createTempFile("upload", "bin");
                    item.write(uploadedFile);

                    final String checksum = checksum(uploadedFile);
                    String ud = System.getenv("UPLOAD_DIR");

                    if (ud == null) ud = "/tmp/uploads";

                    File uploadDir = new File(ud);


                    final File dest = new File(uploadDir, checksum);
                    dest.mkdirs();

                    fileName = fileName.replaceAll("[^a-zA-Z0-9.-]+", "_");
                    System.err.println("target dir:" + dest + " file:" + fileName);

                    if (uploadedFile.renameTo(new File(dest, fileName))) {
                        response.setStatus(SC_OK);
                        response.setContentLength(checksum.length());
                        out.print(checksum);
                    } else {
                        response.setStatus(SC_OK);
                    }
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        } catch (ServletException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
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

    private String checksum(final File uploadedFile) throws NoSuchAlgorithmException, IOException {
        final FileChannel channel = new FileInputStream(uploadedFile).getChannel();
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(channel.map(READ_ONLY, 0, channel.size()));
        StringBuilder buffer = new StringBuilder();
        for (byte b : md.digest()) {
            buffer.append(format("%02x", b));
        }
        return buffer.toString();
    }
}
