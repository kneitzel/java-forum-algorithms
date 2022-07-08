package de.kneitzel.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple TcpServer
 */
public class TcpServer {

    /**
     * Thread that is listenin for new incomming connections.
     */
    private Thread listeningThread;

    private List<TcpServerClient> clients = new ArrayList<>();
    /**
     * Port to listen on.
     */
    private int port;

    public TcpServer(final int port) {
        this.port = port;
    }

    public void listen() throws IOException {
        if (listeningThread != null && listeningThread.isAlive())
            throw new IllegalStateException("TcpServer is already listening!");

        listeningThread = new Thread(this::listenForNewConnections);
        listeningThread.start();
    }

    private void listenForNewConnections() {
        try (ServerSocket socket = new ServerSocket(port)) {
            while (!socket.isClosed()) {
                Socket clientSocket = socket.accept();
                TcpServerClient client = new TcpServerClient(clientSocket, this);
                synchronized (clients) {
                    clients.add(client);
                }
            }
        } catch (IOException ex) {
            System.out.println("Exception when accepting connections: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Handles a client message. This is done inside the clients thread!
     * <p>
     *     This method can "block" because the sendMessage call could block.
     * </p>
     * <p>
     *     The list of clients is copied first to keep the locked state
     *     short so other threads are not blocked while messages are sent.
     * </p>
     * @param tcpServerClient TcpServerClient that received the message.
     * @param message Text that was read.
     */
    public void handleClientMessage(final TcpServerClient tcpServerClient, final String message) {
        List<TcpServerClient> clientsCopy = new ArrayList<>();
        synchronized (clients) {
            clientsCopy.addAll(clients);
        }
        clientsCopy.stream()
                .filter(c -> c != tcpServerClient)
                .forEach(c -> c.sendMessage(message));
    }

    /**
     * Handles the message that a TcpServerClient closed the connection.
     * @param tcpServerClient
     */
    public void clientClosed(TcpServerClient tcpServerClient) {
        synchronized (clients) {
            clients.remove(tcpServerClient);
        }
    }
}
