package de.atns.common.gwt.app.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.gwt.client.WidgetPresenter;
import de.atns.common.security.client.Callback;
import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.event.ServerStatusEvent;
import de.atns.common.security.client.event.ServerStatusEventHandler;
import de.atns.common.security.client.login.LoginPlace;
import de.atns.common.security.client.login.LogoutPlace;
import de.atns.common.security.client.model.UserPresentation;

import java.util.logging.Level;
import java.util.logging.Logger;

import static de.atns.common.security.client.event.ServerStatusEvent.ServerStatus.LOGGED_IN;
import static de.atns.common.security.client.event.ServerStatusEvent.ServerStatus.LOGGED_OUT;
import static de.atns.common.security.client.event.ServerStatusEvent.loggedin;

/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class ApplicationPresenter extends WidgetPresenter<ApplicationPresenter.Display> {

    private static final int CHECK_INTERVAL = 60000;
    private final Place defaultPlace;
    private final Logger LOG = Logger.getLogger("ApplicationPresenter");

    private final Timer checkSessionTimer = new Timer() {
        private boolean running = false;

        @Override public void run() {
            if (!running) {
                running = true;
                checkSession(new Command() {
                    @Override public void execute() {
                        running = false;
                    }
                });
            }
        }
    };

    private final PlaceController placeController;
    private final PlaceHistoryMapper historyMapper;

    @Inject
    public ApplicationPresenter(final PlaceController placeController, final PlaceHistoryMapper historyMapper,
                                @ApplicationDefaultPlace final Place defaultPlace) {
        this.placeController = placeController;
        this.historyMapper = historyMapper;
        this.defaultPlace = defaultPlace;
    }

    public void checkSession(final Command command) {
        dispatcher.execute(new CheckSession(), new AsyncCallback<UserPresentation>() {
            @Override public void onFailure(final Throwable caught) {
                command.execute();
            }

            @Override public void onSuccess(final UserPresentation user) {
                if (!user.isValid()) {
                    doLogin();
                }
                command.execute();
            }
        });
    }

    @Override protected void onBind() {
        super.onBind();

        registerHandler(eventBus.addHandler(ServerStatusEventHandler.TYPE, new ServerStatusEventHandler() {
            @Override public void onServerStatusChange(final ServerStatusEvent event) {
                final ServerStatusEvent.ServerStatus status = event.getStatus();
                if (status == LOGGED_IN) {
                    display.setUser(event.getUser());

                    final Place where = placeController.getWhere();
                    Place newPlace = null;
                    if (where instanceof LoginPlace) {
                        final LoginPlace loginPlace = (LoginPlace) where;
                        newPlace = historyMapper.getPlace(loginPlace.getToken());
                    }
                    if (newPlace == null) {
                        newPlace = (where instanceof LoginPlace) || (where instanceof LogoutPlace) || (where == null)
                                ? defaultPlace : where;
                    }
                    LOG.log(Level.FINE, "after login: " + newPlace);
                    placeController.goTo(newPlace);

                    checkSessionTimer.scheduleRepeating(CHECK_INTERVAL);
                    display.show(true);
                } else if (status == LOGGED_OUT) {
                    checkSessionTimer.cancel();
                    doLogin();
                }
            }
        }));

        dispatcher.execute(new CheckSession(), new Callback<UserPresentation>(display) {
            @Override public void callback(final UserPresentation user) {
                if (user.isValid()) {
                    eventBus.fireEvent(loggedin(user));
                } else {
                    doLogin();
                }
            }
        });
    }

    private void doLogin() {
        final Place where = placeController.getWhere();
        String lastPlace = "";
        if (where != null && !(where instanceof LoginPlace) && !(where instanceof LogoutPlace)) {
            lastPlace = historyMapper.getToken(where);
        }
        placeController.goTo(new LoginPlace(lastPlace));
        display.show(false);
    }

    @Override protected void onUnbind() {
        super.onUnbind();
    }

    public static interface Display extends WidgetDisplay {
        void setUser(UserPresentation user);

        void show(boolean isAuth);

        AcceptsOneWidget getContentWidget();
    }
}
