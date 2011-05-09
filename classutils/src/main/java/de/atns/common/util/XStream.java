package de.atns.common.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * @author mwolter
 * @since 15.04.11 11:15
 */

public class XStream {
// ------------------------------ FIELDS ------------------------------

    private final com.thoughtworks.xstream.XStream xStream = new com.thoughtworks.xstream.XStream();

// -------------------------- STATIC METHODS --------------------------

    public static XStream xstream() {
        final XStream xs = new XStream();
        xs.xStream.autodetectAnnotations(true);
        return xs;
    }

// -------------------------- OTHER METHODS --------------------------

    @SuppressWarnings("unchecked")
    public <T> T fromXML(final String xml) {
        return (T) xStream.fromXML(xml);
    }

    @SuppressWarnings("unchecked")
    public <T> T fromXML(final Reader xml) {
        return (T) xStream.fromXML(xml);
    }

    @SuppressWarnings("unchecked")
    public <T> T fromXML(final InputStream input) {
        return (T) xStream.fromXML(input);
    }

    public Object fromXML(final String xml, final Object root) {
        return xStream.fromXML(xml, root);
    }

    public Object fromXML(final Reader xml, final Object root) {
        return xStream.fromXML(xml, root);
    }

    public Object fromXML(final InputStream xml, final Object root) {
        return xStream.fromXML(xml, root);
    }

    public XStream omitField(final String fieldName, final Class<?>... types) {
        for (final Class<?> type : types) {
            xStream.omitField(type, fieldName);
        }
        return this;
    }

    public XStream simpleName(final Class<?>... types) {
        for (final Class<?> type : types) {
            xStream.alias(type.getSimpleName(), type);
        }
        return this;
    }

    public String toXML(final Object obj) {
        return xStream.toXML(obj);
    }

    public void toXML(final Object obj, final Writer out) {
        xStream.toXML(obj, out);
    }

    public void toXML(final Object obj, final OutputStream out) {
        xStream.toXML(obj, out);
    }
}
