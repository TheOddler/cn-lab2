package cnlab2.server;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

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
        Response resp = new Response(req.getHeader().getVersion(), 200, "OK", "<html> hi </html>");
        System.out.println("Looking for file: " + ROOT_DIR + req.getHeader().getUri().getResource());
        System.out.println("Sending response:\n" + resp.toString());
        getSocket().getOut().writeBytes(resp.toString());
    }
    
    private static final String ROOT_DIR = System.getProperty("user.dir") + "server-root" + File.pathSeparator;
}
