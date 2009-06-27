package mareprint.web.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import org.swfupload.client.File;

import static java.lang.String.valueOf;

/**
 * @author tbaum
 * @since 27.06.2009 17:30:31
 */
public class UploadFilesItemComponent extends HorizontalPanel {
// ------------------------------ FIELDS ------------------------------

    private final String name;
    private final long size;
    private int progress;
    private boolean complete = false;
    private boolean error = false;
    private HTML _progress;
    private SimplePanel _progressBar;
    private HTML _status;

// --------------------------- CONSTRUCTORS ---------------------------

    public UploadFilesItemComponent(final File file) {
        name = file.getName();
        size = file.getSize();

        final HTML _name = new HTML(name);
        _name.setWidth("300px");

        final HTML _size = new HTML(valueOf(size));
        _size.setWidth("150px");
        _size.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

        _progress = new HTML(progress + "%");
        _progress.setWidth("70px");
        _progress.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

        _progressBar = new SimplePanel();
        _progressBar.addStyleName("progressInner");
        _progressBar.setWidth("0%");

        final SimplePanel _progressPanel = new SimplePanel();
        _progressPanel.addStyleName("progressGauge");
        _progressPanel.add(_progressBar);
        _progressPanel.setWidth("131px");

        _status = new HTML("warte");
        _status.setWidth("150px");

        add(_name);
        add(_size);
        add(_progressPanel);
        add(_progress);
        add(_status);
        addStyleName("fileItem");
    }

// -------------------------- OTHER METHODS --------------------------

    public void setComplete() {
        this.complete = true;
        this._status.setText("compete");
        setProgress(100);
    }

    public void setProgress(final int progress) {
        this.progress = progress;
        this._progress.setText(progress + "%");
        this._progressBar.setWidth(progress + "%");
    }

    public void setError() {
        this.error = true;
        this._status.setText("error");
    }

    public void setStart() {
        this._status.setText("übertrage");
    }
}
