package de.atns.common.pdf;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.Map;

/**
 * @author tbaum
 * @since 28.11.2009
 */
public class FopUtil {

    private final Provider<FopFactory> fopFactory;
    private final Provider<TransformerFactory> transformerFactory;

    @Inject
    public FopUtil(final Provider<TransformerFactory> transformerFactory, final Provider<FopFactory> fopFactory) {
        this.transformerFactory = transformerFactory;
        this.fopFactory = fopFactory;
    }

    public void convertFo2Awt(final String data, final URL xsltSource) {
        try {
            final StreamSource streamSource = new StreamSource(xsltSource.openStream());
            final Transformer transformer = transformerFactory.get().newTransformer(streamSource);

            final Fop fop = fopFactory.get().newFop(MimeConstants.MIME_FOP_AWT_PREVIEW);

            transformer.transform(new StreamSource(new StringReader(data)), new SAXResult(fop.getDefaultHandler()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (FOPException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (TransformerException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void print(final String data, final String xsltSource) {
        try {
            final StreamSource streamSource = new StreamSource(new URL(xsltSource).openStream());
            final Transformer transformer = transformerFactory.get().newTransformer(streamSource);

            final Fop fop = fopFactory.get().newFop(MimeConstants.MIME_FOP_PRINT);

            transformer.transform(new StreamSource(new StringReader(data)), new SAXResult(fop.getDefaultHandler()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (FOPException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (TransformerException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void safePdf(final String data, final String templateFile, final OutputStream outputStream,
                        final Map<String, Object> params) {
        try {
            final StreamSource source = new StreamSource(templateFile);
            final Transformer transformer = transformerFactory.get().newTransformer(source);
            for (final Map.Entry<String, Object> p : params.entrySet()) {
                transformer.setParameter(p.getKey(), p.getValue());
            }
            final Fop fop = fopFactory.get().newFop(MimeConstants.MIME_PDF, outputStream);

            transformer.transform(new StreamSource(new StringReader(data)), new SAXResult(fop.getDefaultHandler()));

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (FOPException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (TransformerException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
