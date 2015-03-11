package cnlab2.server;

import java.io.File;
import java.io.IOException;

public class CNLabTwoServer {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        Server s = new Server(port);
        s.start();
    }
    
    public static final String ROOT_DIR = System.getProperty("user.dir") + File.separator + "server-root";
}
