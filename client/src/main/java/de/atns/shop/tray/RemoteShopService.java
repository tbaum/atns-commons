package de.atns.shop.tray;

import com.google.inject.Inject;
import static de.atns.shop.tray.Util.toArray;
import de.atns.shop.tray.data.BankBuchung;
import de.atns.shop.tray.data.Id;
import de.atns.shop.tray.data.ShopConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tbaum
 * @since 05.09.2009 17:42:16
 */
public class RemoteShopService {
// ------------------------------ FIELDS ------------------------------

    private final String remotingHost;
    private final String authToken;
    private final String shopId;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public RemoteShopService(final ShopConfiguration config) {
        remotingHost = config.getRemotingHost();
        authToken = config.getAuth();
        shopId = config.getId();
    }

// -------------------------- OTHER METHODS --------------------------

    public String authenticate(final String user, final String pass) {
        return sendData(
                createPost("authenticate"),
                new NameValuePair("user", user),
                new NameValuePair("pass", pass));
    }

    private String sendData(final PostMethod post, final NameValuePair... params) {
        try {
            final HttpClient client = new HttpClient();
            post.setRequestBody(params);
            post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            client.executeMethod(post);
            if (post.getStatusCode() != 200) {
                throw RemoteException.createError(post.getResponseBodyAsString());
            }
            return post.getResponseBodyAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            post.releaseConnection();
        }
    }

    private PostMethod createPost(final String service) {
        return new PostMethod(remotingHost + "/remoting/trayapp/" + service);
    }

    public String getBestellung(final Id id) {
        return sendData("bestellung", idParam(id));
    }

    private String sendData(final String service, final NameValuePair... nameValuePairs) {
        final PostMethod post = createPost(service);
        post.addRequestHeader("X-Authorization", authToken);
        return sendData(post, nameValuePairs);
    }

    private NameValuePair idParam(final Id id) {
        return new NameValuePair("id", id.getId());
    }

    public String getLabelForId(final Id id) {
        return getLabelForId(id.getId());
    }

    public String getLabelForId(final String id) {
        return sendData("label", idParam(id));
    }

    private NameValuePair idParam(final String id) {
        return new NameValuePair("id", id);
    }

    public Date getLastQuery() {
        return new Date(Long.parseLong(sendData("bank/last")));
    }

    public String getLaufzettel(final Id id) {
        return sendData("laufzettel", idParam(id));
    }

    public String getLieferscheinDetails(final String id) {
        return sendData("lieferschein/details", idParam(id));
    }

    public String getRechnungAndLieferschein(final String id) {
        return sendData("rechnung", idParam(id));
    }

    public Date getRechnungDruckdatum(final String id) {
        final String s = sendData("rechnung/datum", idParam(id));
        return new Date(Long.parseLong(s));
    }

    public long getRechnungNr(final String id) {
        final String s = sendData("rechnung/nr", idParam(id));
        return Long.parseLong(s);
    }

    public String sendTo(final String printerUrl, final String printData, final PrinterTyp printer) {
        final PostMethod post = new PostMethod(printerUrl + "/rawprint");

        return sendData(post,
                new NameValuePair("data", printData),
                new NameValuePair("printer", printer.name()),
                new NameValuePair(TrayApp.SHOP_ID, shopId),
                new NameValuePair(TrayApp.AUTHTOKEN, authToken));
    }

    public long[] synchronizeRecords(final String kto, final List<BankBuchung> records) {
        final List<NameValuePair> request = new ArrayList<NameValuePair>();
        request.add(new NameValuePair("kto", kto));

        for (final BankBuchung buchung : records) {
            buchung.fillRequest(request);
        }

        final String[] rs = sendData("bank/sync", toArray(request, NameValuePair.class)).split("\n");
        return new long[]{Long.valueOf(rs[0]), Long.valueOf(rs[1])};
    }

    public String updateGewicht(final String id, final double gewicht) {
        return sendData("gewicht", new NameValuePair("id", id), new NameValuePair("gewicht", valueOf(gewicht)));
    }
}
