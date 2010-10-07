package de.atns.common.crud.client;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.Inject;
import de.atns.common.crud.client.event.LoadListEvent;
import de.atns.common.crud.client.event.LoadListEventHandler;
import de.atns.common.gwt.client.DefaultWidgetPresenter;
import de.atns.common.gwt.client.ListPresenter;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.security.client.Callback;
import net.customware.gwt.dispatch.shared.Action;

/**
 * @author tbaum
 * @since 05.10.2010
 */
public abstract class ListWidgetPresenter<D extends ListWidgetDisplay<T>, T extends IsSerializable>
        extends DefaultWidgetPresenter<D>
        implements ListPresenter<T> {
// ------------------------------ FIELDS ------------------------------

    protected PagePresenter pagePresenter;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Inject
    public void setPagePresenter(PagePresenter pagePresenter) {
        this.pagePresenter = pagePresenter;
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract Action createLoadAction();

    protected Callback<ListPresentation<T>> loadCallback() {
        return new Callback<ListPresentation<T>>(ListWidgetPresenter.this.getDisplay()) {
            @Override public void callback(final ListPresentation<T> result) {
                LoadListEvent.fireEvent(result, _listEvent(), ListWidgetPresenter.this);
            }
        };
    }

    @Override public D getDisplay() {
        return super.getDisplay();
    }

    protected abstract GwtEvent.Type<LoadListEventHandler<T>> _listEvent();

    @Override protected void onBind() {
        super.onBind();

        pagePresenter.bind(this, _listEvent());
        display.setPagePresenter(pagePresenter.getDisplay());


        registerHandler(display.forSuche(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                pagePresenter.firstPage();
                updateList();
            }
        }));

        registerHandler(display.forPressEnter(new KeyPressHandler() {
            @Override public void onKeyPress(final KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    pagePresenter.firstPage();
                    updateList();
                }
            }
        }));

        registerHandler(eventBus.addHandler(_listEvent(),
                new LoadListEventHandler<T>() {
                    @Override public void onLoad(final ListPresentation<T> event,
                                                 final Object source) {
                        if (event.getEntries() != null && !event.getEntries().isEmpty()) {
                            display.reset();
                            String lastValue = null;
                            for (final T p : event.getEntries()) {
                                lastValue = bindRow(p, lastValue);
                            }
                        }
                    }
                }));

        DeferredCommand.addCommand(new Command() {
            @Override public void execute() {
                updateList();
            }
        });
    }

    protected abstract String bindRow(T p, String lastValue);

    @Override public final void updateList() {
        dispatcher.execute(
                createLoadAction(),
                loadCallback());
    }

    @Override protected void onUnbind() {
        super.onUnbind();
        pagePresenter.unbind();
    }
}
