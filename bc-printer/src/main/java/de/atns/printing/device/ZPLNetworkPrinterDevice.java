package de.atns.printing.device;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Thomas Baum
 */
public class ZPLNetworkPrinterDevice extends ZPLPrinterDevice {
// ------------------------------ FIELDS ------------------------------

    private String adress;

    private int port;

    private OutputStream stream = null;

// --------------------------- CONSTRUCTORS ---------------------------

    public ZPLNetworkPrinterDevice(final String adress, final int port) {
        super();
        this.adress = adress;
        this.port = port;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAdress() {
        return this.adress;
    }

    public int getPort() {
        return this.port;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void processInstructions(final StringBuffer sb) throws IOException {
        final OutputStream stream = openSocket();
        stream.write(sb.toString().getBytes("iso-8859-1"));
        stream.flush();
        //TODO implement close
        //  stream.close();
        //  socket.close();
    }

    private OutputStream openSocket() throws IOException {
        if (stream == null) {
            final Socket socket = new Socket(this.adress, this.port);
            stream = socket.getOutputStream();
        }

        return stream;
    }
}