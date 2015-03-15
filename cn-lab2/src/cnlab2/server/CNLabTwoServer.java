package cnlab2.server;

import java.io.File;
import java.io.IOException;

public class CNLabTwoServer {
    /**
     * The main method, where everything starts.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        // create a server with the given port
        Server s = new Server(port);
        // start the server
        s.start();
    }
    
    public static final String ROOT_DIR = System.getProperty("user.dir") + File.separator + "server-root";
}
