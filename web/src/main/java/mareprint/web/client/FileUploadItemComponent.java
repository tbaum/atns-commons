package mareprint.web.client;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.*;
import org.swfupload.client.File;

import static java.lang.String.valueOf;

/**
 * @author tbaum
 * @since 27.06.2009 17:30:31
 */
public class FileUploadItemComponent extends HorizontalPanel {
// ------------------------------ FIELDS ------------------------------

    private final String name;
    private final long size;
    private int progress;
    private String complete = null;
    private boolean error = false;
    private HTML _progress;
    private SimplePanel _progressBar;
    private HTML _status;

    private ListBox material;
    private TextBox anzahl;

// --------------------------- CONSTRUCTORS ---------------------------

    public FileUploadItemComponent(final File file) {
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

        material = new ListBox();
        material.addItem("Aufkleber");
        material.addItem("Folie");
        material.addItem("Standart");
        material.setSelectedIndex(2);
        material.setVisible(false);

        anzahl = new TextBox();
        anzahl.addKeyPressHandler(new KeyPressHandler() {
            public void onKeyPress(KeyPressEvent event) {
                if (!Character.isDigit(event.getCharCode())) {
                    ((TextBox) event.getSource()).cancelKey();
                }
            }
        });
        anzahl.setVisible(false);
        anzahl.setText("1");
        anzahl.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
        anzahl.setWidth("50px");

        add(_name);
        add(_size);
        add(_progressPanel);
        add(_progress);
        add(_status);

        add(material);
        add(anzahl);
        addStyleName("fileItem");
    }

// -------------------------- OTHER METHODS --------------------------

    public String getHashCode() {
        return complete;
    }

    public boolean isCompleted() {
        return complete != null;
    }

    public void setComplete(String sha) {
        this.complete = sha;
        this._status.setText("fertig");
        setProgress(100);
        material.setVisible(true);
        anzahl.setVisible(true);
    }

    public void setProgress(final int progress) {
        this.progress = progress;
        this._progress.setText(progress + "%");
        this._progressBar.setWidth(progress + "%");
    }

    public void setError() {
        this.error = true;
        this._status.setText("fehler");
    }

    public void setStart() {
        this._status.setText("übertrage");
    }
}
