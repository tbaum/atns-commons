package de.atns.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.jar.Manifest;

public abstract class Utils {

    public final static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    public static final Log LOG = LogFactory.getLog(Utils.class);
    private static final SecureRandom random;

    static {
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class getOriginalClass(final Object ob) {
        if (ob instanceof HibernateProxy) {
            return ((HibernateProxy) ob).getHibernateLazyInitializer().getPersistentClass();
        }
        return ob.getClass();
    }

    public static <TYPE> TYPE getBean(final ApplicationContext context, final String beanName) {
        return (TYPE) context.getBean(beanName);
    }

    public static Document createDocument() {
        try {
            final DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            return docBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeQuietly(final Connection con) {
        if (con == null) {
            return;
        }
        try {
            con.close();
        } catch (SQLException e) {
            // Nothing we can do here
        }
    }

    public static void closeQuietly(final PreparedStatement pstmtQuery) {
        if (pstmtQuery == null) {
            return;
        }
        try {
            pstmtQuery.close();
        } catch (SQLException e) {
            //
        }
    }

    public static MessageDigest createMD5Digest() {
        final MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        algorithm.reset();
        return algorithm;
    }

    public static String encodeMessageDigest(final MessageDigest algorithm) {
        return encodeBigInteger(new BigInteger(algorithm.digest()).abs());
    }

    public static String encodeBigInteger(BigInteger data) {
        data = data.abs();
        final StringBuilder ret = new StringBuilder();
        final char[] digits = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        final BigInteger radix = BigInteger.valueOf(digits.length);
        BigInteger[] rt = new BigInteger[]{data};
        while (!rt[0].equals(BigInteger.ZERO)) {
            rt = rt[0].divideAndRemainder(radix);
            ret.append(digits[rt[1].intValue()]);
        }
        return ret.toString();
    }

    public static Date getEndOfDay(final Date to) {
        return getDateWithHourAndMinute(to, 23, 59);
    }

    private static Date getDateWithHourAndMinute(final Date from, final int hour, final int minute) {
        final Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(from);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    public static Date getStartOfDay(final Date from) {
        return getDateWithHourAndMinute(from, 0, 0);
    }

    public static String randomString(final int length, final int chars) {
        final StringBuilder s = new StringBuilder();
        for (int j = 0; j < length; j++) {
            s.append(Integer.toString(random.nextInt(chars), chars));
        }
        return s.toString();
    }

    public static void appendTextNode(final Document dd, final Element page, final String nodeName,
                                      final String nodeValue) {
        final Element element;
        page.appendChild(element = dd.createElement(nodeName));
        element.appendChild(dd.createTextNode(nodeValue != null ? nodeValue : ""));
    }

    public static void appendTextNode(final Document dd, final Element page, final String nodeName,
                                      final String nodeValue, final String attributeName, final String attributeValue) {
        final Element element;
        page.appendChild(element = dd.createElement(nodeName));
        element.appendChild(dd.createTextNode(nodeValue));
        element.setAttribute(attributeName, attributeValue);
    }

    public static void runInEventThread(final Runnable closure) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(closure);
        } else {
            closure.run();
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T[] toArray(final Class<T> clazz, final Collection<T> col) {
        final ArrayList<T> entries = new ArrayList<T>(col);
        return entries.toArray((T[]) Array.newInstance(clazz, entries.size()));
    }

    public static String loadFile(final String file) {
        FileInputStream inputStream = null;
        try {
            //noinspection IOResourceOpenedButNotSafelyClosed
            inputStream = new FileInputStream(file);
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int rd;
            final byte[] buffer = new byte[2048];
            while ((rd = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, rd);
            }
            return new String(outputStream.toByteArray());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            closeSilent(inputStream);
        }
    }

    public static void closeSilent(final InputStream aIs) {
        try {
            if (aIs != null) {
                aIs.close();
            }
        } catch (IOException e) {
            //
        }
    }

    public static void closeSilent(final Reader aBr) {
        if (aBr != null) {
            try {
                aBr.close();
            } catch (IOException e) {
                //
            }
        }
    }

    public static void closeSilent(final Socket socket) {
        try {
            socket.shutdownInput();
        } catch (Exception e) {
            //
        }
        try {
            socket.shutdownOutput();
        } catch (Exception e) {
            //
        }
        try {
            socket.close();
        } catch (Exception e) {
            //
        }
    }

    public static void closeSilent(final FileWriter fw) {
        if (fw != null) {
            try {
                fw.close();
            } catch (IOException e) {
//
            }
        }
    }//TODO handle ex

    public static void writeFile(final String file, final String data) {
        FileOutputStream outputStream = null;
        try {
            //noinspection IOResourceOpenedButNotSafelyClosed
            outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Exception Occured:", JOptionPane.ERROR_MESSAGE);
            //throw new RuntimeException(ex);
        } finally {
            closeSilent(outputStream);
        }
    }

    public static void closeSilent(final OutputStream aIs) {
        try {
            if (aIs != null) {
                aIs.close();
            }
        } catch (IOException e) {
            //
        }
    }

    public static String arrayToDelimitedString(final Object[] objects, final String s) {
        return collectionToDelimitedString(Arrays.asList(objects), s);
    }

    public static String collectionToDelimitedString(final Iterable missing, final String s) {
        final StringBuilder result = new StringBuilder(0);
        final Iterator iterator = missing.iterator();
        while (iterator.hasNext()) {
            final Object token = iterator.next();
            result.append(token);
            if (iterator.hasNext()) {
                result.append(s);
            }
        }
        return result.toString();
    }

    public static Double max(final Double a, final Double b) {
        if (a == null && b == null) {
            return null;
        }
        if (b == null) {
            return a;
        }
        if (a == null) {
            return b;
        }
        return Math.max(a, b);
    }

    public static Double min(final Double a, final Double b) {
        if (a == null && b == null) {
            return null;
        }
        if (b == null) {
            return a;
        }
        if (a == null) {
            return b;
        }
        return Math.min(a, b);
    }

    public static <T> T[] asArray(final T... args) {
        return args;
    }

    public static <T> List<T> list(final List<T> result, final T... element) {
        Collections.addAll(result, element);
        return result;
    }

    public static <T> T construct(final Class<? extends T> judgeClass, final Object... args) {
        final Class[] argsC = getClasses(args);

        try {
            final Constructor<? extends T> con = judgeClass.getConstructor(argsC);
            return con.newInstance(args);
        } catch (Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e, e);
            }
            throw new RuntimeException(e);
        }
    }

    public static Class[] getClasses(final Object... args) {
        final Class[] argsC = new Class[args.length];

        for (int i = 0; i < args.length; i++) {
            argsC[i] = args[i].getClass();
        }
        return argsC;
    }

    public static <T> T construct(final Class<? extends T> judgeClass, final Class[] argsC, final Object... args) {
        try {
            final Constructor<? extends T> con = judgeClass.getConstructor(argsC);
            return con.newInstance(args);
        } catch (Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e, e);
            }
            throw new RuntimeException(e);
        }
    }

    public static void checkRange(final Integer address, final int min, final int max) {
        if (address == null || address < min || address > max) {
            throw new IllegalArgumentException(String.format("%d outside range %d..%d", address, min, max));
        }
    }

    public static <T> Set<T> asSet(final T... x) {
        return new HashSet<T>(Arrays.asList(x));
    }

    public static Object invoke(final Method method, final Object object, final Object... arguments) {
        try {
            return method.invoke(object, arguments);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            final Throwable t = e.getTargetException();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            throw new RuntimeException(t);
        }
    }

    public static <BEAN> BEAN newInstance(final Class<BEAN> aClass) {
        try {
            return aClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class[] createInterfaceList(final Class superClass, final Class... classes) {
        final List<Class> interfaces = new ArrayList<Class>();
        if (superClass.isInterface()) {
            interfaces.add(superClass);
        } else {
            Collections.addAll(interfaces, superClass.getInterfaces());
        }
        Collections.addAll(interfaces, classes);
        return interfaces.toArray(new Class[interfaces.size()]);
    }

    public static String readManifestValue(final Class clazz, final String attribute) {
        try {
            final URL archiveUrl = clazz.getProtectionDomain().getCodeSource().getLocation();
            final URL manifestUrl = new URL(String.format("jar:%s!/META-INF/MANIFEST.MF", archiveUrl));
            final Manifest manifest = new Manifest(manifestUrl.openStream());
            final String value = manifest.getMainAttributes().getValue(attribute);
            if (value != null) {
                return value;
            }
        } catch (IOException e) {
            //
        }
        return "not-available";
    }

    public static RuntimeException convertToRuntimeException(final Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        if (e instanceof InvocationTargetException) {
            return convertToRuntimeException(((InvocationTargetException) e).getTargetException());
        }
        //noinspection ThrowableInstanceNeverThrown
        return new RuntimeException(e);
    }

    public static String getXmlAsString(final Element label) throws TransformerException {
        final StringWriter writer = new StringWriter();
        final DOMSource domSource = new DOMSource(label);
        final StreamResult streamResult = new StreamResult(writer);
        final TransformerFactory tf = TransformerFactory.newInstance();
        final Transformer serializer = tf.newTransformer();
        serializer.transform(domSource, streamResult);
        return writer.getBuffer().toString();
    }

    public static <T> String join(final String delim, final T... rs) {
        final StringBuilder rss = new StringBuilder();
        for (int i = 0; i < rs.length; i++) {
            rss.append(rs[i]);
            if (i + 1 < rs.length) {
                rss.append(delim);
            }
        }
        return rss.toString();
    }
}
