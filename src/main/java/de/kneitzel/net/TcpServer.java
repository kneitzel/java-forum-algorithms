package de.kneitzel.net;

import de.kneitzel.util.SafeClose;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple TcpServer
 */
public class TcpServer implements AutoCloseable {

    /**
     * Thread that is listenin for new incomming connections.
     */
    private Thread listeningThread;

    private List<TcpServerClient> clients = new ArrayList<>();
    /**
     * Port to listen on.
     */
    private int port;

    /**
     * The server socket used to accept incomming connections.
     */
    private ServerSocket socket;

    /**
     * Creates a new instance of TcpServer
     * @param port Port to use.
     */
    public TcpServer(final int port) {
        this.port = port;
    }

    /**
     * Starts the listening on the server.
     */
    public void start() throws IOException {
        if (listeningThread != null && listeningThread.isAlive())
            throw new IllegalStateException("TcpServer is already listening!");

        listeningThread = new Thread(this::listenForNewConnections);
        listeningThread.start();
    }

    private void listenForNewConnections() {
        try {
            socket = new ServerSocket(port);
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
     * @param tcpServerClient TcpServerClient that received the message.
     * @param message Text that was read.
     */
    public void handleClientMessage(final TcpServerClient tcpServerClient, final String message) {
        getClientListCopy().stream()
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

    /**
     * Creates a copy of the list of all TcpServerClients.
     * @return A copy of the List of all TcpServerClients.
     */
    private List<TcpServerClient> getClientListCopy() {
        synchronized (clients) {
            return clients.stream().toList();
        }
    }

    /**
     * Signals the TcpServer that the socket should be closed and the thread handling the socket should be stopped.
     * <p>
     *     Also closes all client connections!
     * </p>
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        SafeClose.close(socket);
        getClientListCopy().forEach(TcpServerClient::close);
    }
}
