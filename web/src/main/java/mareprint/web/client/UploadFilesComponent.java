package mareprint.web.client;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.swfupload.client.File;
import org.swfupload.client.SWFUpload;
import org.swfupload.client.UploadBuilder;
import org.swfupload.client.UploadStats;
import org.swfupload.client.event.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tbaum
 * @since 27.06.2009 17:30:39
 */
public class UploadFilesComponent extends VerticalPanel implements FileQueuedHandler, UploadCompleteHandler,
        UploadProgressHandler, UploadErrorHandler, UploadStartHandler {
// ------------------------------ FIELDS ------------------------------

    private final Map<String, UploadFilesItemComponent> files = new HashMap<String, UploadFilesItemComponent>();
    private SWFUpload upload;

// --------------------------- CONSTRUCTORS ---------------------------

    public UploadFilesComponent(final DivElement placeHolder) {
        UploadBuilder builder = new UploadBuilder();

        //     builder.setFileTypes("**.png;*.jpg;*.jpeg;*.gif;*.tif;*.pdf;*");
        //     builder.setFileTypesDescription("Bilder");

        builder.setButtonPlaceholderID(placeHolder.getId());
        builder.setButtonWidth(109);
        builder.setButtonHeight(21);

        builder.setButtonImageURL("select.png");

        builder.setButtonAction(SWFUpload.ButtonAction.SELECT_FILES);

        builder.setUploadProgressHandler(this);
        builder.setUploadCompleteHandler(this);
        builder.setFileQueuedHandler(this);
        builder.setUploadErrorHandler(this);
        builder.setUploadStartHandler(this);

        this.upload = builder.build();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface FileQueuedHandler ---------------------

    public void onFileQueued(final FileQueuedEvent fileQueuedEvent) {
        final File file = fileQueuedEvent.getFile();
        final UploadFilesItemComponent status = new UploadFilesItemComponent(file);
        files.put(file.getId(), status);

        add(status);

        if (!upload.getStats().isInProgress()) {
            upload.startUpload();
        }

        updateDebug();
    }

// --------------------- Interface UploadCompleteHandler ---------------------

    public void onUploadComplete(final UploadCompleteEvent uploadCompleteEvent) {
        final File file = uploadCompleteEvent.getFile();

        final UploadFilesItemComponent st = files.get(file.getId());
        if (st != null) {
            st.setComplete();
        }


        if (upload.getStats().getFilesQueued() > 0) {
            upload.startUpload();
        }

        updateDebug();
    }

// --------------------- Interface UploadErrorHandler ---------------------

    public void onUploadError(final UploadErrorEvent uploadErrorEvent) {
        final File file = uploadErrorEvent.getFile();
        final UploadFilesItemComponent st = files.get(file.getId());
        if (st != null) {
            st.setError();
        }

        if (upload.getStats().getFilesQueued() > 0) {
            upload.startUpload();
        }

        updateDebug();
    }

// --------------------- Interface UploadProgressHandler ---------------------

    public void onUploadProgress(final UploadProgressEvent uploadProgressEvent) {
        final File file = uploadProgressEvent.getFile();
        double progress = 100 * uploadProgressEvent.getBytesComplete() / uploadProgressEvent.getBytesTotal();
        final UploadFilesItemComponent st = files.get(file.getId());
        if (st != null) {
            st.setProgress((int) progress);
        }
        updateDebug();
    }

// --------------------- Interface UploadStartHandler ---------------------

    public void onUploadStart(final UploadStartEvent uploadStartEvent) {
        final File file = uploadStartEvent.getFile();
        final UploadFilesItemComponent st = files.get(file.getId());
        if (st != null) {
            st.setStart();
        }
        updateDebug();
    }

// -------------------------- OTHER METHODS --------------------------

    private void updateDebug() {
        final UploadStats s = upload.getStats();

        debug(
                "queued " + s.getFilesQueued() + ", " +
                        "queued errors " + s.getQueueErrors() + ", " +
                        "upload cancelled " + s.getUploadsCancelled() + ", " +
                        "upload successful " + s.getSuccessfulUploads() + ", "
                        + "upload errors " + s.getUploadErrors() + ", " +
                        "in progress " + s.isInProgress());
    }

    protected void debug(String s) {
    }
}
