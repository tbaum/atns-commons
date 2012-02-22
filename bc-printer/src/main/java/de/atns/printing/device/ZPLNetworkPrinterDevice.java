package de.atns.printing.device;

import de.atns.common.fileutils.FileUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Thomas Baum
 */
public class ZPLNetworkPrinterDevice extends ZPLPrinterDevice {

    private final String adress;

    private final int port;

    private OutputStream stream = null;
    private Socket socket;

    public ZPLNetworkPrinterDevice(final String adress, final int port, final int dpi) {
        super(dpi);
        this.adress = adress;
        this.port = port;
    }

    public String getAdress() {
        return this.adress;
    }

    public int getPort() {
        return this.port;
    }

    public void close() {
        FileUtil.closeSilent(socket);
        FileUtil.closeSilent(stream);
    }

    @Override protected void processInstructions(final StringBuffer sb) throws IOException {
        final OutputStream stream = openSocket();
        stream.write(sb.toString().getBytes("iso-8859-1"));
        stream.flush();
        //TODO implement close
        //  stream.close();
        //  socket.close();
    }

    private OutputStream openSocket() throws IOException {
        if (stream == null) {
            socket = new Socket(this.adress, this.port);
            stream = socket.getOutputStream();
        }

        return stream;
    }
}
