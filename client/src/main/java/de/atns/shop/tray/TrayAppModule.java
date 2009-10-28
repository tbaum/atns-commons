package de.atns.shop.tray;

import com.google.inject.multibindings.MapBinder;
import static com.google.inject.multibindings.MapBinder.newMapBinder;
import com.google.inject.servlet.LocalRequestScoped;
import static com.google.inject.servlet.LocalServletScopes.LOCAL_REQUEST;
import com.google.inject.servlet.ServletModule;
import de.atns.shop.tray.action.*;

/**
 * @author tbaum
 * @since 27.09.2009 17:57:16
 */
public class TrayAppModule extends ServletModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configureServlets() {
        bindScope(LocalRequestScoped.class, LOCAL_REQUEST);

        bind(Integer.class).annotatedWith(ServerPort.class).toInstance(18080);

        filter("/*").through(JSONPFilter.class);
        filter("/*").through(JSONFilter.class);

        serve("/*").with(DispatchServlet.class);

        final MapBinder<String, Action> actionProvider = newMapBinder(binder(), String.class, Action.class);
        actionProvider.addBinding("/bankabgleich").to(BankabgleichAction.class);
        actionProvider.addBinding("/dummy/rawprint").to(DummyPrintAction.class);
        actionProvider.addBinding("/print/bestellung").to(PrintBestellungAction.class);
        actionProvider.addBinding("/print/label").to(PrintLabelAction.class);
        actionProvider.addBinding("/print/laufzettel").to(PrintLaufzettelAction.class);
        actionProvider.addBinding("/print/rechnung").to(PrintRechnungAction.class);
        actionProvider.addBinding("/rawprint").to(PrintAction.class);
    }
}

