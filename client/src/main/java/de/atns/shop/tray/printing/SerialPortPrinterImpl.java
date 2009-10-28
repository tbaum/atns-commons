package de.atns.shop.tray.printing;

//import com.sun.comm.Win32Driver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.comm.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import static java.util.Arrays.asList;

public class SerialPortPrinterImpl implements Printer {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(SerialPortPrinterImpl.class);

    private OutputStream out;
    private InputStream rawIn;
    private final PipedInputStream in = new PipedInputStream();

// --------------------------- CONSTRUCTORS ---------------------------

    public SerialPortPrinterImpl(final String portName) {
        try {
            openPort(portName);
            startPipeThread();
            initPrinter();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void openPort(final String portName) throws IOException, UnsupportedCommOperationException, PortInUseException, NoSuchPortException {
        System.setSecurityManager(null);

        final CommDriver commDriver = null;//new Win32Driver();
        commDriver.initialize();

        final CommPortIdentifier commportidentifier = CommPortIdentifier.getPortIdentifier(portName);

        final CommPort port = commportidentifier.open("atns.shop.TrayApp", 2000);

        final SerialPort serialport = (SerialPort) port;

        serialport.setSerialPortParams(9600, 8, 1, 0);
        serialport.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_OUT);

        this.out = port.getOutputStream();
        this.rawIn = port.getInputStream();
    }

    private void startPipeThread() {
        final Thread t = new Thread() {
            @Override
            public void run() {
                PipedOutputStream po = null;
                try {
                    po = new PipedOutputStream(in);
                    int read;
                    final byte[] buffer = new byte[1024];
                    while ((read = rawIn.read(buffer)) != -1) {
                        po.write(buffer, 0, read);
                        synchronized (in) {
                            in.notifyAll();
                        }
                    }
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                } finally {
                    try {
                        if (po != null) {
                            po.close();
                        }
                    } catch (IOException e) {
                        //
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }

    private void initPrinter() throws IOException, InterruptedException {
        out.write(("Y96,N,8,1\neR$,0\nI8,1,049\nO\n\n\nN\n\n\n\nN\n\n\n").getBytes());
        checkStatus();
        dumpForms();
        dumpGraphics();
        checkStatus();
    }

    public void dumpForms() throws IOException, InterruptedException {
        System.err.println("stored forms:");
        final List<String> l = sendAndRead("UF\n");
        for (final String o : l.subList(1, l.size())) {
            System.err.println("- '" + o + "'");
        }
    }

    private List<String> sendAndRead(final String send) throws IOException, InterruptedException {
        out.write(send.getBytes());
        out.flush();

        waitForData(10000);
        if (in.available() == 0) {
            throw new RuntimeException("Fehler bei der Kommunikation mit dem Drucker!");
        }
        final ByteArrayOutputStream bo = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        while (in.available() > 0) {
            final int read = in.read(buffer);
            bo.write(buffer, 0, read);
            waitForData(500);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(displayByteArray(bo.toByteArray()));
        }
        final String[] ret = new String(bo.toByteArray()).split("\r\n");
        return asList(ret);
    }

    public void waitForData(final long wait) throws IOException, InterruptedException {
        synchronized (in) {
            if (in.available() == 0) {
                in.wait(wait);
            }
        }
    }

    private static String displayByteArray(final byte[] buffer) {
        final StringBuilder asc = new StringBuilder(1024);
        for (final byte b : buffer) {
            if (b > ' ') {
                asc.append((char) b);
            } else {
                asc.append('<').append(b < 16 ? "0" : "").append(Integer.toString(b, 16)).append('>');
            }
        }
        return asc.toString();
    }

    public void dumpGraphics() throws IOException, InterruptedException {
        System.err.println("stored graphics:");
        final List<String> l = sendAndRead("UG\n");
        for (final String o : l.subList(1, l.size())) {
            System.err.println("- '" + o + "'");
        }
    }

    public void checkStatus() throws InterruptedException, IOException {
        LOG.debug("check:");
        checkResponse("^ee\n");
    }

    public Set<String> checkResponse(final String send, final String... required) throws InterruptedException, IOException {
        final Set<String> req = new TreeSet<String>();
        Collections.addAll(req, required);

        final List<String> ret = sendAndRead(send);
        for (final String r : ret) {
            req.remove(r);
        }
        return req;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Printer ---------------------

    @Override public void printData(final String out) {
        try {
            write(out);
            waitForData(500);
            clearInputQueue();
            flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public void clearInputQueue() throws InterruptedException, IOException {
        waitForData(500);
        final byte[] buffer = new byte[1024];
        while (in.available() > 0) {
            //noinspection ResultOfMethodCallIgnored
            in.read(buffer);
            waitForData(500);
        }
    }

    public void flush() throws IOException {
        out.flush();
    }

    public void sendResource(final String url) throws IOException {
        final InputStream resource = new URL(url).openStream();
        final byte[] buffer = new byte[1024];
        int read;
        while ((read = resource.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        resource.close();
    }

    public void storeForm(final String name, final InputStream fi) throws IOException, InterruptedException {
        System.err.println("store form " + name);
        final byte[] data = readFile(fi);
        if (containsForm(name)) {
            System.err.println("remove for update");
            writeFormat("FK\"%s\"\n", name);
            checkStatus();
            if (containsForm(name)) {
                System.err.println("Formular konnte nicht entfernt werden");
                throw new RuntimeException();
            }
        }

        writeFormat("FS\"%s\"\n", name);

        final String[] lines = new String(data).split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.length() > 0 && !line.startsWith("*")) {
                System.err.println("## " + line);
                write(line + "\n");
            }
        }
        write("FE\n");
        write("\n");

        if (!containsForm(name)) {
            System.err.println("Formular konnte nicht gespeichert werden");
            throw new RuntimeException();
        }
    }

    private byte[] readFile(final InputStream fi) throws IOException {
        final byte[] buffer = new byte[1024];
        final ByteArrayOutputStream bo = new ByteArrayOutputStream();
        int size;
        while ((size = fi.read(buffer)) > 0) {
            bo.write(buffer, 0, size);
        }
        return bo.toByteArray();
    }

    public void writeFormat(final String data, final Object... args) throws IOException {
        write(String.format(data, args));
    }

    public void write(final String data) throws IOException {
        final byte[] b = data.getBytes("cp850");
        System.err.println("##" + Arrays.toString(b));
        write(b);
    }

    private boolean containsForm(final String name) throws IOException, InterruptedException {
        final List<String> result = sendAndRead("UF\n");
        for (int i = 1; i < result.size(); i++) {
            if (result.get(i).equals(name)) return true;
        }
        return false;
    }

    public void storeGraphics(final String name, final InputStream fi) throws IOException, InterruptedException {
        System.err.println("store graphics " + name);
        final byte[] data = readFile(fi);
        if (containsGraphic(name)) {
            System.err.println("remove for update");

            writeFormat("GK\"%s\"\n", name);
            checkStatus();
            if (containsGraphic(name)) {
                System.err.println("Grafik konnte nicht entfernt werden");
                throw new RuntimeException();
            }
        }
        writeFormat("GM\"%s\"%d\n", name, data.length);
        write(data);
        write("\n");

        if (!containsGraphic(name)) {
            System.err.println("Grafik konnte nicht gespeichert werden");
            throw new RuntimeException();
        }
    }

    public void write(final byte[] bytes) throws IOException {
        out.write(bytes);
    }

    private boolean containsGraphic(final String name) throws IOException, InterruptedException {
        final List<String> result = sendAndRead("UG\n");
        for (int i = 1; i < result.size(); i++) {
            if (result.get(i).equals(name)) return true;
        }
        return false;
    }
}
