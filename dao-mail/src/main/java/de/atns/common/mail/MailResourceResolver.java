package de.atns.common.mail;

import de.atns.common.util.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * User: tbaum
 * Date: 15.04.2009
 * Time: 02:46:36
 */
public class MailResourceResolver {

    private static final Log LOG = LogFactory.getLog(MailResourceResolver.class);
    private String marker = "embed:";

    public String getMarker() {
        return marker;
    }

    public void setMarker(final String marker) {
        this.marker = marker;
    }

    public ResolvedMail extractResources(final String str, final Map<String, Object> context) {
        final Map<String, MailResource> result = new HashMap<String, MailResource>();
        final StringBuilder strx = new StringBuilder();
        int start = 0;

        int found = 0;
        while ((found = str.indexOf(marker, found)) != -1) {
            strx.append(str.substring(start, found));
            found += marker.length();
            int pos = str.length();
            for (final String s : new String[]{")", "\"", "'", " ", "\n", "\r", "\t"}) {
                final int p = str.indexOf(s, found);
                if (p > -1) {
                    pos = Math.min(pos, p);
                }
            }
            final String adr = str.substring(found, pos);
            found = pos + 1;
            strx.append(adr);
            start = pos;

            if (!result.containsKey(adr)) {
                try {
                    result.put(adr, createResource(adr, context));
                } catch (IOException e) {
                    LOG.error(format("error loading resource %s (%s)", adr, e.getMessage()));
                }
            }
        }
        strx.append(str.substring(start));
        return new ResolvedMail(ArrayUtils.toArray(MailResource.class, result.values()), strx.toString());
    }

    protected MailResource createResource(final String adr, final Map<String, Object> context) throws IOException {
        return new MailResource(adr, readURL("http:" + adr));
    }

    private byte[] readURL(final String address) throws IOException {
        LOG.debug("reading URL " + address);
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final URL u = new URL(address);
            final InputStream is = u.openStream();
            final byte[] buf = new byte[1500];
            int rd;
            while ((rd = is.read(buf)) != -1) {
                bos.write(buf, 0, rd);
            }
            is.close();
            return bos.toByteArray();
        } catch (IOException e) {
            LOG.error(format("error reading url %s(%s)", address, e));
            return new byte[0];
        }
    }

    public class ResolvedMail {
        public final MailResource[] result;
        public final String message;

        private ResolvedMail(final MailResource[] result, final String message) {
            this.result = result;
            this.message = message;
        }
    }
}
