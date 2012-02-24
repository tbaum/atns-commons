package de.atns.common.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.apps.FOURIResolver;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author tbaum
 * @since 28.11.2009
 */
public class SecurityManagerWorkaroundResolver extends FOURIResolver {

    private static final Log LOG = LogFactory.getLog(SecurityManagerWorkaroundResolver.class);

    // TODO Remove me!
    // This is a workaround for the security manager problem under windows.
    // While the webstart app has all-permissions an exception is thrown
    // upon File.exists -> hence the resolving doesn't work with the base
    // FOURIResolver. When this problem is solved this class can be removed!
    @Override public Source resolve(final String href, final String base) throws TransformerException {
        if (base == null && href != null && href.startsWith("http://")) {
            try {
                final URL absoluteURL = new URL(href);
                final String effURL = absoluteURL.toExternalForm();
                final URLConnection connection = absoluteURL.openConnection();
                connection.setAllowUserInteraction(false);
                connection.setDoInput(true);
                updateURLConnection(connection, href);
                connection.connect();
                return new StreamSource(connection.getInputStream(), effURL);
            } catch (FileNotFoundException fnfe) {
                //Note: This is on "debug" level since the caller is supposed to handle this
                LOG.debug("File not found: " + href);
            } catch (IOException ioe) {
                LOG.error("Error with opening URL '" + href + "': " + ioe.getMessage(), ioe);
            }
        }
        return super.resolve(href, base);
    }
}
