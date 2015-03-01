package cnlab2.server;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class Handler implements Runnable {
    
    private SmartSocket socket;
    
    public Handler(Socket socket) throws IOException {
        setSocket(new SmartSocket(socket));
    }
    
    public SmartSocket getSocket() {
        return socket;
    }
    
    private void setSocket(SmartSocket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            while (!getSocket().getSocket().isClosed()) {
                Request r = new Request(getSocket().getIn());
                System.out.println("Got request:\n" + r.toString());
                switch (r.getHeader().getCommand()) {
                    case "GET":
                        handleGet(r);
                        break;
                    case "HEAD":
                        break;
                    case "PUT":
                        break;
                    case "POST":
                        break;
                    case "OPTIONS":
                        break;
                    case "TRACE":
                        break;
                    default:
                        System.out.println("Command not supported: " + r.getHeader().getCommand());
                }
            }
            
        } catch (IOException e) {
            System.out.println("Something went wrong while handling a request.");
            e.printStackTrace();
        }
    }
    
    private void handleGet(Request req) throws IOException {
        String pathStr = req.getHeader().getUri().getResource();
        if (req.getHeader().getUri().getResource().equals("/")) {
            pathStr = "index.html";
        }
        
        // Find path of file
        Path path = Paths.get(ROOT_DIR + File.separator + pathStr);
        System.out.println("Looking for file: " + path.toString());
        
        // Read file to string
        List<String> strings = Files.readAllLines(path, Charset.defaultCharset());
        StringBuilder contentBuilder = new StringBuilder();
        for (String s : strings) {
            contentBuilder.append(s);
            contentBuilder.append("\n");
        }
        String content = contentBuilder.toString();
        
        // Construct a response
        Response resp = new Response(req.getHeader().getVersion(), 200, "OK", content);
        
        // Send the response
        System.out.println("Sending response:\n" + resp.toString());
        getSocket().getOut().writeBytes(resp.toString());
    }
    
    private static final String ROOT_DIR = System.getProperty("user.dir") + File.separator + "server-root";
}
