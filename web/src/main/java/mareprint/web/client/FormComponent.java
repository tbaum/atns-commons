package mareprint.web.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Cookies;
import static mareprint.web.client.Util.horizontal;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * @author tbaum
 * @since 29.06.2009 19:12:44
 */
public abstract class FormComponent extends FlexTable implements ChangeHandler {
// ------------------------------ FIELDS ------------------------------

    private final Mareprint app;
    private final Label heading = Util.head2("");
    private final Label errors = new Label();
    private final List<String> err = new ArrayList<String>();

// --------------------------- CONSTRUCTORS ---------------------------

    public FormComponent(final Mareprint app) {
        this.app = app;
        this.errors.addStyleName("error-message");
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ChangeHandler ---------------------

    public void onChange(final ChangeEvent changeEvent) {
        validate();
    }

// -------------------------- OTHER METHODS --------------------------

    protected boolean addError(final String errorKey, final boolean error) {
        if (error && !err.contains(errorKey)) {
            err.add(errorKey);
            return false;
        } else {
            return true;
        }
    }

    protected void addRow(final String s1, final Widget... textBox) {
        int pos = getRowCount();
        setHTML(pos, 0, s1);
        getFlexCellFormatter().setWidth(pos, 0, "100px");
        setWidget(pos, 1, horizontal(textBox));

        for (Widget widget : textBox) {
            if (widget instanceof HasChangeHandlers) {
                HasChangeHandlers hasChangeHandlers = (HasChangeHandlers) widget;
                hasChangeHandlers.addChangeHandler(this);
            }
        }
    }

    public boolean hasErrors() {
        return err.size() > 0;
    }

    protected void insertErrors() {
        int pos = getRowCount();
        setWidget(pos, 0, errors);
        getFlexCellFormatter().setColSpan(pos, 0, 2);
    }

    protected void insertHeading(String heading) {
        int pos = getRowCount();
        setHeading(heading);
        setWidget(pos, 0, this.heading);
        getFlexCellFormatter().setColSpan(pos, 0, 2);
    }

    public void setHeading(final String heading) {
        this.heading.setText(heading);
    }

    protected TextBox lower(final TextBox t) {
        final String s = t.getText().toLowerCase();
        if (!s.equals(t.getText())) {
            t.setText(s);
        }
        return t;
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        validate();
    }

    protected void validate() {
        err.clear();
        checkFields();

        if (err.size() == 1) {
            errors.setText("Bitte füllen Sie das Feld  " + err.get(0) + " aus!");
            errors.setVisible(true);
        } else if (hasErrors()) {
            errors.setText("Bitte füllen Sie die Felder " + Util.join(err, ", ", " und ") + " aus!");
            errors.setVisible(true);
        } else {
            errors.setVisible(false);
        }

        if (app != null) app.validate();
    }

    protected abstract void checkFields();

    protected TextBox trim(final TextBox t) {
        final String s = t.getText().trim().replaceAll("[ \\t]+", " ");
        if (!s.equals(t.getText())) {
            t.setText(s);
        }
        return t;
    }

    protected boolean validateLength(final TextBox t, final String errorKey, final int lenght) {
        return addError(t, errorKey, t.getText().length() != lenght);
    }

    protected boolean addError(final Widget t, final String errorKey, final boolean error) {
        if (error && !err.contains(errorKey)) {
            err.add(errorKey);
            t.addStyleName("error");
            return false;
        } else {
            t.removeStyleName("error");
            return true;
        }
    }

    protected boolean validateMinLength(final TextBox t, final String errorKey, final int min) {
        return addError(t, errorKey, t.getText().length() < min);
    }

    protected boolean validateRegexp(final TextBox t, final String errorKey, final String regex) {
        return addError(t, errorKey, t.getText().replaceAll(regex, "").length() != 0);
    }
    protected boolean validateIn(final TextBox t, final String errorKey, final String...values) {
        final boolean ok = Arrays.asList(values).contains(t.getText());
        return addError(t, errorKey, !ok);
    }
}
