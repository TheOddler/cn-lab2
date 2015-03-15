package cnlab2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    
    /**
     * Constructor with minimum neede information
     * @param port
     */
    public Server(int port) {
        setPort(port);
    }
    
    /**
     * Start running the server
     * @throws IOException
     */
    public void start() throws IOException {
        // Create a welcome socket to listen on for connections
        ServerSocket welcomeSocket = new ServerSocket(getPort());
        
        try {
            // Create a bunch of threads to handle incoming connections
            ExecutorService executor = Executors.newFixedThreadPool(4);
            while (true) {
                // Accept the incoming connection
                Socket connectionSocket = welcomeSocket.accept();
                // Create a worker to handle the connection
                Runnable worker = new Worker(connectionSocket);
                // Start the worker on a new thread so this one isn't blocked
                // and we can continue to accept new connections.
                executor.execute(worker);
            }
        } finally {
            // close the welcome socket when closing the server
            welcomeSocket.close();
        }
    }
    
    public int getPort() {
        return port;
    }
    
    private void setPort(int port) {
        this.port = port;
    }
    
}
