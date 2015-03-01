package cnlab2.server;

import java.io.IOException;
import java.net.Socket;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class Worker implements Runnable {
    
    private SmartSocket socket;
    
    public Worker(Socket socket) throws IOException {
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
                Handler h = null;
                switch (r.getHeader().getCommand()) {
                    case "GET":
                        h = new GetHandler(getSocket(), r);
                        break;
                    case "HEAD":
                        h = new HeadHandler(getSocket(), r);
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
                        return;
                }
                Response resp = h.handle();
                getSocket().getOut().writeBytes(resp.toString());
            }
            
        } catch (IOException e) {
            System.out.println("Something went wrong while handling a request.");
            e.printStackTrace();
        }
    }
    
    private void handleGet(Request req) throws IOException {
    }
    
}
