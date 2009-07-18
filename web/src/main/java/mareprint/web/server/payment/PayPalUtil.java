package mareprint.web.server.payment;

import mareprint.web.client.model.Adresse;
import mareprint.web.client.model.Auftrag;
import mareprint.web.client.model.Kontakt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import static java.lang.String.format;
import java.net.URLEncoder;

public class PayPalUtil {

    private static final Log LOG = LogFactory.getLog(PayPalUtil.class);

    public static String generatePayPalRequestString(final Auftrag auftrag, final String returnPath,
                                                     final String businessAccount, final String cancelPath,
                                                     final String notifyUrl, final String payPalUrl, final String description) {
        final StringBuilder url = createUrl(businessAccount, payPalUrl, returnPath, cancelPath, notifyUrl);

        mitAuftrag(url, auftrag, description);

        mitAdresse(url, auftrag.getLieferAdresse());

        mitKontakt(url, auftrag.getKontakt());

        return url.toString();
    }

    private static StringBuilder createUrl(String businessAccount, String payPalUrl, String returnPath, String cancelPath, String notifyUrl) {
        final StringBuilder url = new StringBuilder(payPalUrl + "?");
        param(url, "cmd", "_xclick");
        param(url, "business", businessAccount);
        param(url, "currency_code", "EUR");
        param(url, "upload", 1);
        param(url, "address_override", 1);
        // todo return path, cancelURL
        param(url, "return_path", returnPath);
        param(url, "cancel_path", cancelPath);
        param(url, "notify_url", notifyUrl);
        return url;
    }

    private static void mitAuftrag(final StringBuilder url, final Auftrag auftrag, final String description) {
        param(url, "invoice", auftrag.getRechnungsNr());
        param(url, "shipping_1", auftrag.getVersand());
        param(url, "amount", -auftrag.getBruttoPreis());
        param(url, "item_name", description);
        param(url, "quantity", 1);
    }

    private static void mitAdresse(final StringBuilder url, final Adresse adresse) {
        param(url, "address1", adresse.getStrasse());
        param(url, "address2", adresse.getZusatz());
        param(url, "city", adresse.getOrt());
        param(url, "zip", adresse.getPlz());
        param(url, "country", adresse.getLand());
    }

    private static void mitKontakt(final StringBuilder url, final Kontakt kontakt) {
        param(url, "first_name", kontakt.getVorname());
        param(url, "last_name", kontakt.getNachname());
    }

    private static void param(final StringBuilder url, final String param, final Object value) {
        url.append(format("%s=%s&", param, encode(value)));
    }

    private static String encode(final Object value) {
        if (value == null) return "";
        final String text = value.toString();
        try {
            return URLEncoder.encode(text, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
            return text;
        }
    }

}
