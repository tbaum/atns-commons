package de.atns.common.util;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class SimpleResourceLoader extends ResourceLoader {

    @Override public long getLastModified(final Resource resource) {
        return 0;
    }

    @Override public InputStream getResourceStream(final String s) throws ResourceNotFoundException {
        byte[] data;
        try {
            data = s.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            data = s.getBytes();
        }
        return new ByteArrayInputStream(data);
    }

    @Override public void init(final ExtendedProperties extendedProperties) {
        //
    }

    @Override public boolean isSourceModified(final Resource resource) {
        return false;
    }
}
