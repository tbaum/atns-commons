package mareprint.web.server.persist;

import com.thoughtworks.xstream.XStream;

/**
 * @author Michael Hunger
 * @since 29.06.2009
 */
public class XmlWriter {
    public void writeXml(Object value) {
        XStream xstream = new XStream();

        String xml = xstream.toXML(value);

    }
}
