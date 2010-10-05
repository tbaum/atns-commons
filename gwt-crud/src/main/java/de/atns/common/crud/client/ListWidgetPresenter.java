package de.atns.common.crud.client;

import com.google.inject.Inject;
import de.atns.common.gwt.client.DefaultWidgetPresenter;
import de.atns.common.gwt.client.ListPresenter;

/**
 * Created by IntelliJ IDEA.
 * User: tbaum
 * Date: 05.10.2010
 * Time: 01:00:08
 * To change this template use File | Settings | File Templates.
 */
public abstract class ListWidgetPresenter<D extends ListWidgetDisplay> extends DefaultWidgetPresenter<D>
        implements ListPresenter {
// ------------------------------ FIELDS ------------------------------

    protected PagePresenter pagePresenter;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Inject
    public void setPagePresenter(PagePresenter pagePresenter) {
        this.pagePresenter = pagePresenter;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void onBind() {
        super.onBind();

        pagePresenter.bind(this);
        display.setPagePresenter(pagePresenter.getDisplay());
    }

    @Override protected void onUnbind() {
        super.onUnbind();
        pagePresenter.unbind();
    }
}
