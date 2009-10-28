package de.atns.shop.tray;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.shop.tray.data.ShopConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tbaum
 * @since 26.09.2009 17:33:36
 */
@Singleton public class AuthtokenCache {
// ------------------------------ FIELDS ------------------------------

    private final Map<String, String> cache = new HashMap<String, String>();
    //    @Inject private Injector injector;
    private AuthenticateDialog dialog;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Inject public void setDialog(final AuthenticateDialog dialog) {
        this.dialog = dialog;
    }

// -------------------------- OTHER METHODS --------------------------

    public void authenticate(final ShopConfiguration currentShop) {
        cache.remove(currentShop.getId());
        dialog.authenticate(currentShop.getId());
    }

    public String authenticate(final ShopConfiguration configuration, final String user, final String pass) {
        final RemoteShopService remote = new RemoteShopService(configuration);

        // try {
        //    remote.setCurrent(configuration.getRemotingHost(), -1, configuration.getId());

        final String token = remote.authenticate(user, pass);
        put(configuration.getId(), token);
        return token;
        // } catch (IOException e) {
        //   e.printStackTrace();
        //   return null;
        //  }
    }

    public void put(final String shopId, final String authToken) {
        cache.put(shopId, authToken);
    }

    public String update(final String id) {
        String authToken1 = cache.get(id);
        if (authToken1 == null) {
            dialog.authenticate(id);
            authToken1 = cache.get(id);

            if (authToken1 == null) {
                throw new RemoteException("auth aboarded!");
            }
        }

        return authToken1;
        //   remote.setCurrent(configuration.getRemotingHost(), authToken1, shopId);
    }

    public void updateTokenCache(final HttpServletRequest httpServletRequest) {
        final String shopId = httpServletRequest.getParameter(TrayApp.SHOP_ID);
        final String authToken = httpServletRequest.getParameter(TrayApp.AUTHTOKEN);
        System.err.println("updating sec token " + shopId + " -> " + authToken);
        if (shopId != null && authToken != null)
            put(shopId, authToken);
    }
}
