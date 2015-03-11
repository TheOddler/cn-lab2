package cnlab2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    
    public Server(int port) {
        setPort(port);
    }
    
    public void start() throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(getPort());
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            Runnable worker = new Worker(connectionSocket);
            executor.execute(worker);
        }
    }
    
    public int getPort() {
        return port;
    }
    
    private void setPort(int port) {
        this.port = port;
    }
    
}
