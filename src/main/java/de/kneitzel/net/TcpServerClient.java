package de.kneitzel.net;

import de.kneitzel.util.SafeClose;

import java.io.*;
import java.net.Socket;

/**
 * Client of the TcpServerClient.
 */
public class TcpServerClient implements AutoCloseable {

    /**
     * Socket for communication.
     */
    private Socket socket;

    /**
     * TcpServer that handles all client connections including this one.
     */
    private TcpServer server;

    /**
     * Thread that is listening for new messages.
     */
    private Thread listeningThread;

    /**
     * Writer for new messages.
     */
    private BufferedWriter writer;

    /**
     * Creates a new instance of a TcpServerClient.
     * @param socket Socket of the connection.
     * @param server TcpServer that handles all client connections including this one.
     */
    public TcpServerClient(final Socket socket, final TcpServer server) throws IOException {
        this.socket = socket;
        this.server = server;

        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        listeningThread = new Thread(this::listenForMessages);
        listeningThread.start();
    }

    /**
     * Listen for messages
     */
    private void listenForMessages() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            while (true) {
                server.handleClientMessage(this, reader.readLine());
            }
        } catch (IOException ex) {
            System.out.println("Exception in TcpServerClient thread: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }

        close();
    }

    /**
     * Sends a message to the connected server
     * <p>
     *     This call can block / take some time.
     * </p>
     * @param message Message to send.
     */
    public void sendMessage(final String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            System.out.println("Exception writing to Server: " + ex.getMessage());
            ex.printStackTrace(System.err);
            close();
        }
    }

    /**
     * Signals, that the thread should end and all resources should be closed.
     */
    @Override
    public void close() {
        SafeClose.close(writer);
        SafeClose.close(socket);
        server.clientClosed(this);
    }
}
