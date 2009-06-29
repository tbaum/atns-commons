package mareprint.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import static com.google.gwt.i18n.client.Dictionary.getDictionary;
import static com.google.gwt.user.client.DOM.createDiv;
import org.swfupload.client.File;
import org.swfupload.client.SWFUpload;
import org.swfupload.client.UploadBuilder;
import org.swfupload.client.UploadStats;
import org.swfupload.client.event.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * @author tbaum
 * @since 27.06.2009 17:30:39
 */
public class FileUploadComponent extends FormComponent implements FileQueuedHandler, UploadSuccessHandler,
        UploadProgressHandler, UploadErrorHandler, UploadStartHandler {
// ------------------------------ FIELDS ------------------------------

    private final Map<String, FileUploadItemComponent> files = new HashMap<String, FileUploadItemComponent>();
    private SWFUpload upload;
    private final UploadBuilder builder = new UploadBuilder();

// --------------------------- CONSTRUCTORS ---------------------------

    //  private final Mareprint app;
    public FileUploadComponent(final Mareprint app) {
        super(app);

        insertHeading("Dateien hochladen / Format + Anzahl");
        final DivElement placeHolder = createDiv().cast();
        placeHolder.setId("swf-upload-" + this.hashCode());
        getElement().appendChild(placeHolder);

        //     builder.setFileTypes("**.png;*.jpg;*.jpeg;*.gif;*.tif;*.pdf;*");
        //     builder.setFileTypesDescription("Bilder");

        builder.setButtonWidth(109);
        builder.setButtonHeight(21);

        builder.setButtonImageURL("select.png");

        builder.setButtonAction(SWFUpload.ButtonAction.SELECT_FILES);

        builder.setUploadURL(GWT.getModuleBaseURL() + "upload;jsessionid=" + getDictionary("config").get("sessionId"));
        builder.setUploadProgressHandler(this);
        builder.setUploadSuccessHandler(this);
        builder.setFileQueuedHandler(this);
        builder.setUploadErrorHandler(this);
        builder.setUploadStartHandler(this);
        builder.setButtonPlaceholderID(placeHolder.getId());
        //   insertErrors();
        //  validate();
        //  this.app = app;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface FileQueuedHandler ---------------------

    public void onFileQueued(final FileQueuedEvent fileQueuedEvent) {
        final File file = fileQueuedEvent.getFile();
        final FileUploadItemComponent status = new FileUploadItemComponent(file);
        files.put(file.getId(), status);

        int pos = getRowCount();
        setWidget(pos, 0, status);
        getFlexCellFormatter().setColSpan(pos, 0, 2);

        startOrContinueUpload();

        validate();
    }

// --------------------- Interface UploadErrorHandler ---------------------

    public void onUploadError(final UploadErrorEvent uploadErrorEvent) {
        final File file = uploadErrorEvent.getFile();
        final FileUploadItemComponent st = files.get(file.getId());
        if (st != null) {
            st.setError();
        }

        startOrContinueUpload();

        validate();
    }

// --------------------- Interface UploadProgressHandler ---------------------

    public void onUploadProgress(final UploadProgressEvent uploadProgressEvent) {
        final File file = uploadProgressEvent.getFile();
        double progress = 100 * uploadProgressEvent.getBytesComplete() / uploadProgressEvent.getBytesTotal();
        final FileUploadItemComponent st = files.get(file.getId());
        if (st != null) {
            st.setProgress((int) progress);
        }
        //   validate();
    }

// --------------------- Interface UploadStartHandler ---------------------

    public void onUploadStart(final UploadStartEvent uploadStartEvent) {
        final File file = uploadStartEvent.getFile();
        final FileUploadItemComponent st = files.get(file.getId());
        if (st != null) {
            st.setStart();
        }
        validate();
    }

// --------------------- Interface UploadSuccessHandler ---------------------

    public void onUploadSuccess(final UploadSuccessEvent uploadSuccessEvent) {
        final File file = uploadSuccessEvent.getFile();

        final FileUploadItemComponent st = files.get(file.getId());
        if (st != null) {
            st.setComplete(uploadSuccessEvent.getServerData());
        }

        startOrContinueUpload();

        validate();
    }

// -------------------------- OTHER METHODS --------------------------

    protected void checkFields() {
        if (upload == null) {
            addError("start", true);
            return;
        }

        final UploadStats s = upload.getStats();
        if (s.isInProgress()) {
            addError("in-progress", true);
            return;
        }

        for (FileUploadItemComponent fileUploadItemComponent : files.values()) {
            if (fileUploadItemComponent.isCompleted()) {
                return;
            }
        }

        addError("empty", true);
    }

    public List<String> getHashCodes() {
        final List<String> result = new ArrayList<String>();

        for (FileUploadItemComponent fileUploadItemComponent : files.values()) {
            if (fileUploadItemComponent.isCompleted()) {
                result.add(fileUploadItemComponent.getHashCode());
            }
        }
        return result;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        this.upload = builder.build();
    }

    private void startOrContinueUpload() {
        if (upload != null && upload.getStats().getFilesQueued() > 0) {
            upload.startUpload();
        }
    }
}
