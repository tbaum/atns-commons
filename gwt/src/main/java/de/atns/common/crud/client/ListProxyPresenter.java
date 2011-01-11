package de.atns.common.crud.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.requestfactory.shared.Request;
import com.google.inject.Inject;
import de.atns.common.crud.client.event.LoadListProxyEvent;
import de.atns.common.crud.client.event.LoadListProxyEventHandler;
import de.atns.common.gwt.client.WidgetPresenter;

import java.util.List;

/**
 * @author tbaum
 * @since 05.10.2010
 */
public abstract class ListProxyPresenter<D extends ListProxyDisplay<T>, T extends EntityProxy> extends WidgetPresenter<D> {
// ------------------------------ FIELDS ------------------------------

    protected PageProxyPresenter pageProxyPresenter;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Inject
    public void setPageProxyPresenter(final PageProxyPresenter pageProxyPresenter) {
        this.pageProxyPresenter = pageProxyPresenter;
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract Request createLoadAction();

    @Override public D getDisplay() {
        return super.getDisplay();
    }

    @Override protected void onBind() {
        super.onBind();

        pageProxyPresenter.bind(this);
        display.setPageProxyPresenter(pageProxyPresenter.getDisplay());

        registerHandler(display.forSuche(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                pageProxyPresenter.firstPage();
                updateList();
            }
        }));

        registerHandler(display.forPressEnter(new EnterKeyPressHandler() {
            @Override protected void onEnterPressed() {
                pageProxyPresenter.firstPage();
                updateList();
            }
        }));

        registerHandler(eventBus.addHandler(_listEvent(),
                new LoadListProxyEventHandler<T>() {
                    @Override public void onLoad(final List<T> event, final Object source) {
                        display.reset();
                        if (event != null) {
                            String lastValue = null;
                            for (final T presentation : event) {
                                lastValue = bindRow(presentation, lastValue);
                            }
                        }
                    }
                }));

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override public void execute() {
                if (isBound()) {
                    updateList();
                }
            }
        });
    }

    protected abstract GwtEvent.Type<LoadListProxyEventHandler<T>> _listEvent();

    protected abstract String bindRow(T presentation, String lastValue);

    public final void updateList() {
        createLoadAction().fire(loadReceiver());
    }

    protected Receiver<List<T>> loadReceiver() {
        return new Receiver<List<T>>() {
            @Override public void onSuccess(final List<T> result) {
                LoadListProxyEvent.fireEvent(result, _listEvent(), ListProxyPresenter.this);
            }
        };
    }

    @Override protected void onUnbind() {
        super.onUnbind();
        pageProxyPresenter.unbind();
    }
}
