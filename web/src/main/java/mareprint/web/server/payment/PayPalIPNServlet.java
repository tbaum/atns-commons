package mareprint.web.server.payment;

import mareprint.web.client.model.Auftrag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class PayPalIPNServlet extends HttpServlet {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(PayPalIPNServlet.class);
    private static final long serialVersionUID = 5697378745206712636L;

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse httpServletResponse) throws ServletException, IOException {
        final URLConnection connection = openVerifyConnection();
        writeParams(connection, renderRequestParams(request));
        final String result = readResult(connection);
        if (result.equals("VERIFIED")) {
            verifiedPayment(request);
        } else {
            LOG.error("PAYPAL NOT VERIFIED");
        }
    }

    private String renderRequestParams(final HttpServletRequest request) throws UnsupportedEncodingException {
        final StringBuilder paramString = new StringBuilder("cmd=_notify-validate");
        final Map<String, String> params = request.getParameterMap();
        for (final Map.Entry<String, String> param : params.entrySet()) {
            paramString.append(format(param));
        }
        return paramString.toString();
    }

    private String format(Map.Entry<String, String> param) throws UnsupportedEncodingException {
        return String.format("%s=%s&", param.getKey(), encode(param.getValue()));
    }

    private String encode(final String text) throws UnsupportedEncodingException {
        return URLEncoder.encode(text, "ISO-8859-1");
    }

    private String readResult(final URLConnection uc) throws IOException {
        final InputStream is = uc.getInputStream();
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(is));
            return in.readLine();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return "ERROR";
        } finally {
            if (is != null)
                is.close();
        }
    }

    private void writeParams(final URLConnection uc, final String str) throws IOException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(uc.getOutputStream());
            pw.println(str);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private URLConnection openVerifyConnection() throws IOException {
        final URL u = new URL("https://www.paypal.com/cgi-bin/webscr");
        final URLConnection uc = u.openConnection();
        uc.setDoOutput(true);
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        return uc;
    }

    private void verifiedPayment(final HttpServletRequest request) {
        final String rechnungsNummer = request.getParameter("invoice");
        final String tx = request.getParameter("txn_id");
        final String status = request.getParameter("payment_status");
        final int amount = Integer.parseInt(request.getParameter("mc_gross")); // todo in cent ?
        final Auftrag auftrag = AuftragStorage.lade(getPath(),rechnungsNummer);
        if (auftrag!=null)
            auftrag.setzeBezahlt(tx, status, amount);
        else LOG.error("Keinen Auftrag mit nummer "+rechnungsNummer+" gefunden");
    }

    private String getPath() {
        return getServletConfig().getInitParameter("auftrags_pfad");
    }

}
