package de.atns.printing.environment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class StatusListener extends Thread {
// ------------------------------ FIELDS ------------------------------

//    private PrinterEnvironment env;

    private boolean listen = true;

    private final ServerSocket socket;

// --------------------------- CONSTRUCTORS ---------------------------

    public StatusListener(final ServerSocket socket, @SuppressWarnings("unused") final PrinterEnvironment env) {
//        this.env = env;
        this.socket = socket;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public void setListen(final boolean listen) {
        this.listen = listen;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Runnable ---------------------

    @Override
    public void run() {
        while (this.listen) {
            try {
                final Socket clientSocket = this.socket.accept();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                String response = reader.readLine();
                // env.handleResponse(response);
                reader.close();
                clientSocket.close();
            } catch (Exception e) {
                // TODO Handle Exception
                e.printStackTrace();
            }
        }
    }
}
