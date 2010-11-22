package de.atns.common.gwt.app.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.gwt.client.WidgetPresenter;
import de.atns.common.security.client.Callback;
import de.atns.common.security.client.LoginPlace;
import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.action.UserLogout;
import de.atns.common.security.client.event.*;
import de.atns.common.security.client.model.UserPresentation;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.gwt.user.client.Window.addWindowClosingHandler;
import static de.atns.common.security.client.event.ServerStatusEventHandler.ServerStatus.NO_LOGIN;
import static de.atns.common.security.client.event.ServerStatusEventHandler.ServerStatus.UNAVAILABLE;

/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class ApplicationPresenter extends WidgetPresenter<ApplicationPresenter.Display> {
// ------------------------------ FIELDS ------------------------------

    //TODO
    // public static final BestellteProduktePlace DEFAULT_PLACE = BestellteProduktePlace.ALL;
    private static final int CHECK_INTERVAL = 15000;
    private final Logger LOG = Logger.getLogger("ApplicationPresenter");

    private WidgetPresenter<? extends WidgetDisplay> activePresenter;

    private final Timer checkSessionTimer = new Timer() {
        private boolean running = false;

        @Override
        public void run() {
            if (!running) {
                running = true;
                checkSession(new Command() {
                    @Override
                    public void execute() {
                        running = false;
                    }
                });
            }
        }
    };

    private ServerStatusEventHandler.ServerStatus serverState = null;

    private final PlaceController placeController;
    private final PlaceHistoryMapper historyMapper;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public ApplicationPresenter(PlaceController placeController, PlaceHistoryMapper historyMapper) {
        this.placeController = placeController;
        this.historyMapper = historyMapper;
    }

// -------------------------- OTHER METHODS --------------------------

    public void doLogout() {
        display.show(false);

        Cookies.removeCookie("l");
        dispatcher.execute(new UserLogout(), new Callback<UserPresentation>() {
            @Override
            public void callback(final UserPresentation user) {
                eventBus.fireEvent(new LogoutEvent(user));
                //   placeController.goTo(new LoginPlace(""));
            }
        });
    }

    @Override
    protected void onBind() {
        super.onBind();
        registerHandler(eventBus.addHandler(LoginEventHandler.TYPE, new LoginEventHandler() {
            @Override
            public void onLogin(final LoginEvent event) {
                display.setUser(event.getUser());
                checkSessionTimer.scheduleRepeating(CHECK_INTERVAL);
            }
        }));

        registerHandler(eventBus.addHandler(LogoutEventHandler.TYPE, new LogoutEventHandler() {
            @Override
            public void onLogout(final LogoutEvent event) {
                checkSessionTimer.cancel();
                eventBus.fireEvent(new ServerStatusEvent(NO_LOGIN));
            }
        }));

        registerHandler(addWindowClosingHandler(new Window.ClosingHandler() {
            @Override
            public void onWindowClosing(final Window.ClosingEvent event) {
                // doLogout();
            }
        }));


        registerHandler(eventBus.addHandler(ServerStatusEventHandler.TYPE, new ServerStatusEventHandler() {
            @Override
            public void onServerStatusChange(final ServerStatusEvent event) {
                if (event.getStatus() != serverState) {
                    serverState = event.getStatus();

                    switch (event.getStatus()) {
                        case AVAILABLE:
                            if (event.getUser().isValid()) {
                                display.show(true);
                                eventBus.fireEvent(new LoginEvent(event.getUser()));
                                final Place where = placeController.getWhere();
                                Place newPlace = null;
                                if (where instanceof LoginPlace) {
                                    LoginPlace loginPlace = (LoginPlace) where;
                                    newPlace = historyMapper.getPlace(loginPlace.getToken());
                                }
                                if (newPlace == null) {
                                    //TODO
                                    //    newPlace = DEFAULT_PLACE;
                                }
                                LOG.log(Level.FINE, "after login: " + newPlace);
                                placeController.goTo(newPlace);
                            } else {
                                display.show(false);
                                eventBus.fireEvent(new LogoutEvent(event.getUser()));
                            }


                        case UNAVAILABLE:
                            display.setServerStatus(event.getStatus());
                            break;

                        case NO_LOGIN:
                            doLogin();
                            checkSessionTimer.cancel();
                            break;
                    }
                }
            }
        }));


        checkSession(new Command() {
            @Override
            public void execute() {
            }
        });
    }

    private void doLogin() {
        placeController.goTo(new LoginPlace(""));
    }

    public void checkSession(final Command command) {
        dispatcher.execute(new CheckSession(), new AsyncCallback<UserPresentation>() {
            @Override
            public void onFailure(final Throwable caught) {
                eventBus.fireEvent(new ServerStatusEvent(UNAVAILABLE));
                command.execute();
            }

            @Override
            public void onSuccess(final UserPresentation user) {
                eventBus.fireEvent(new ServerStatusEvent(user));
                command.execute();
            }
        });
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends WidgetDisplay {
        void setServerStatus(ServerStatusEventHandler.ServerStatus serverStatus);

        void setUser(UserPresentation user);

        void show(boolean isAuth);

        AcceptsOneWidget getPanel();
    }
}
