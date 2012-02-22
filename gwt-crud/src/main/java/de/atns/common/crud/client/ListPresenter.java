package de.atns.common.crud.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.Inject;
import de.atns.common.crud.client.event.LoadListEvent;
import de.atns.common.crud.client.event.LoadListEventHandler;
import de.atns.common.gwt.client.WidgetPresenter;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.security.client.Callback;
import net.customware.gwt.dispatch.shared.Action;

/**
 * @author tbaum
 * @since 05.10.2010
 */
public abstract class ListPresenter<D extends ListDisplay<T>, T extends IsSerializable> extends WidgetPresenter<D> {

    protected PagePresenter pagePresenter;

    @Inject public void setPagePresenter(final PagePresenter pagePresenter) {
        this.pagePresenter = pagePresenter;
    }

    @Override protected void onBind() {
        super.onBind();

        pagePresenter.bind(this);
        display.setPagePresenter(pagePresenter.getDisplay());

        registerHandler(display.forSuche(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                pagePresenter.firstPage();
                updateList();
            }
        }));

        registerHandler(display.forPressEnter(new EnterKeyPressHandler() {
            @Override protected void onEnterPressed() {
                pagePresenter.firstPage();
                updateList();
            }
        }));

        registerHandler(eventBus.addHandler(_listEvent(),
                new LoadListEventHandler<T>() {
                    @Override public void onLoad(final ListPresentation<T> event, final Object source) {
                        display.reset();
                        if (event.getEntries() != null) {
                            String lastValue = null;
                            for (final T presentation : event.getEntries()) {
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

    protected abstract GwtEvent.Type<LoadListEventHandler<T>> _listEvent();

    protected abstract String bindRow(T presentation, String lastValue);

    public final void updateList() {
        final Action loadAction = createLoadAction();
        if (loadAction != null) {
            dispatcher.execute(loadAction, loadCallback());
        }
    }

    protected abstract Action createLoadAction();

    protected Callback<ListPresentation<T>> loadCallback() {
        return new Callback<ListPresentation<T>>(ListPresenter.this.getDisplay()) {
            @Override public void callback(final ListPresentation<T> result) {
                LoadListEvent.fireEvent(result, _listEvent(), ListPresenter.this);
            }
        };
    }

    @Override public D getDisplay() {
        return super.getDisplay();
    }

    @Override protected void onUnbind() {
        super.onUnbind();
        pagePresenter.unbind();
    }
}
