package de.atns.shop.tray;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import de.atns.shop.tray.gui.TrayAppPopupMenu;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.ServletRequest;
import javax.swing.*;
import java.awt.*;
import static java.awt.SystemTray.getSystemTray;
import static java.awt.Toolkit.getDefaultToolkit;

@Singleton
public class TrayApp {
// ------------------------------ FIELDS ------------------------------

    public static final String SHOP_ID = "shopid";
    public static final String AUTHTOKEN = "authtoken";
    @Inject private EnterWeightDialog enterWeightDialog;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public TrayApp(@ServerPort final int port, final Injector injector) throws Exception {
        getDefaultToolkit().getSystemEventQueue().push(new EventQueueProxy());


        if (SystemTray.isSupported()) {
            getSystemTray().add(createTrayIcon());
        } else {
            throw new RuntimeException("SystemTray is not supported");
        }


        final Server server = new Server(port);
        final ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

        root.addFilter(GuiceFilter.class, "/*", 0);
        root.addServlet(DefaultServlet.class, "/");
        root.addEventListener(new GuiceServletContextListener() {
            @Override
            protected Injector getInjector() {
                return injector;
            }
        });

        server.start();
    }

    private TrayIcon createTrayIcon() {
        final Image idleImage = new ImageIcon(getClass().getResource("/images/tray-icon.png")).getImage();


        final TrayIcon trayIcon = new TrayIcon(idleImage, "Trayapp", new TrayAppPopupMenu());
        trayIcon.setImageAutoSize(true);
        return trayIcon;
    }

// --------------------------- main() method ---------------------------

    public static void main(final String[] args) {
        try {
            System.setSecurityManager(null);
            final Injector injector = Guice.createInjector(new TrayAppModule());
            final TrayApp trayApp = injector.getInstance(TrayApp.class);

            //In.createInjector(TrayAppModule.class)
            //   new TrayApp(0);
        } catch (Exception e) {
            Util.displayException(e);
        }
        //  new JavaConfigApplicationContext(TrayappConfig.class);
    }
}
