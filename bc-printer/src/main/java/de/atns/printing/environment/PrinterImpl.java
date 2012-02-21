package de.atns.printing.environment;

import de.atns.printing.device.ZPLNetworkPrinterDevice;
import de.atns.printing.document.Mode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class PrinterImpl extends AbstractPrinterImpl {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(PrinterImpl.class);

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Printer ---------------------

    @Override public boolean canPrint(final Label lf) {
        if (this.material.equals(lf.getMaterial())) {
            try {
                updateState();
            } catch (Exception e) {
                return false;
            }
            if (this.state.isPrintableState(lf.getMaterial().getMode()))
                return true;
        }
        return false;
    }

    @Override public void reset() {
        updatePrinterParameter(this.material);
        safePrinterConfiguration();
        sendToPrinter("^XA~JR~XZ");
    }

    @Override public void safePrinterConfiguration() {
        sendToPrinter("^XA~JC^JUS^XZ");
    }

    @Override public boolean statusOk() {
        return this.state.statusOk();
    }

    @Override public boolean testMaterial(final Material mat) throws IOException {
        updatePrinterParameter(mat);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOG.error("Interrupted while waiting: " + e.getMessage());
        }
        updateState();
        LOG.info(this.state.toString());
        return this.state.statusOk();
    }

    @Override public void updateState() throws IOException {
        String line1 = null;
        String line2 = null;
        final Socket s = getSocket();
        final OutputStream stream = s.getOutputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        stream.write("^XA~HS^XZ".getBytes("iso-8859-1"));
        line1 = reader.readLine();
        line2 = reader.readLine();
        // No relevant data in last line
        reader.readLine();
        reader.close();
        stream.close();
        s.close();
        parseLine1(line1);
        parseLine2(line2);
    }

    @Override public boolean waitForPrinter() {
        int waiting = 0;
        LOG.info("START WAITING: Status ok? " + this.state.statusOk() + ", isPrinting? " + this.state.isPrinting() + ", jobs: "
                + this.state.getJobs() + ", waiting (max 120): " + waiting);
        do {
            LOG.info("STILL WAITING: Status ok? " + this.state.statusOk() + ", isPrinting? " + this.state.isPrinting()
                    + ", jobs: " + this.state.getJobs() + ", waiting (max 120): " + waiting);
            waiting++;
            try {
                Thread.sleep(1000);
                updateState();
            } catch (Exception e) {
                LOG.error("Interrupted while waiting: " + e.getMessage());
            }
        } while (this.state.statusOk() && this.state.isPrinting() && waiting < 120);
        LOG.info("RETURNING: Status ok? " + this.state.statusOk() + ", isPrinting? " + this.state.isPrinting() + ", jobs: "
                + this.state.getJobs() + ", waiting (max 120): " + waiting);
        return this.state.statusOk();
    }

// -------------------------- OTHER METHODS --------------------------

    private void parseLine1(final String line1) {
        final String[] tokens = line1.split(",");
        this.state.setPaperOut(tokens[1]);
        this.state.setPaused(tokens[2]);
        this.state.setBufferFull(tokens[5]);
        this.state.setCorruptRam(tokens[9]);
        this.state.setLowTemperature(tokens[10]);
        this.state.setHighTemperature(tokens[11]);
    }

    private void parseLine2(final String line2) {
        final String[] tokens = line2.split(",");
        this.state.setHeadUp(tokens[2]);
        if ("1".equals(tokens[4])) {
            this.state.setRibbonOut(tokens[3]);
            this.state.setRibbonIn(false);
        } else {
            this.state.setRibbonIn(tokens[3]);
            this.state.setRibbonOut(false);
        }

        this.state.setThermoTransferMode(tokens[4]);

        LOG.info((Integer.parseInt(tokens[8])) + " Jobs remaining");
        this.state.setJobs(Integer.parseInt(tokens[8]));
    }

    private void updatePrinterParameter(final Material mat) {
        sendToPrinter("^XA" + //
                (mat.getMode().equals(Mode.TT) ? "^MTT" : "^MTD") + // mode
                "^MNY" + // media tracking: non continuous
                "^MMT" + // print mode: tear off
                "^MFF,F" + // media feed: C: calibrate, Feed first web
                "^XZ"); // safe
    }

    public void sendToPrinter(final String str) {
        try {
            final Socket s = getSocket();
            final OutputStream stream = s.getOutputStream();
            stream.write(str.getBytes("iso-8859-1"));
            stream.close();
            s.close();
        } catch (Exception e) {
            LOG.error("Unable to send to printer: " + e.getMessage());
        }
    }

    Socket getSocket() throws IOException {
        final String address = ((ZPLNetworkPrinterDevice) getDevice()).getAdress();
        final int port = ((ZPLNetworkPrinterDevice) getDevice()).getPort();
        final SocketAddress sa = new InetSocketAddress(address, port);
        final Socket sock = new Socket();
        sock.connect(sa, 10000);
        return sock;
    }
}
