package cnlab2.server;

import java.io.IOException;
import java.net.Socket;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.SocketClosedException;

public class Worker implements Runnable {
    
    private SmartSocket socket;
    
    /**
     * Contructor with minimal needed info.
     * @param socket The socket used for this connection
     * @throws IOException
     */
    public Worker(Socket socket) throws IOException {
        setSocket(new SmartSocket(socket));
    }
    
    public SmartSocket getSocket() {
        return socket;
    }
    
    private void setSocket(SmartSocket socket) {
        this.socket = socket;
    }
    
    /**
     * Run the Worker
     */
    @Override
    public void run() {
        try {
            // continue as long as the socket is open
            while (!getSocket().getSocket().isClosed()) {
                // Read the incoming request
                Request r = new Request(getSocket());
                System.out.println("Got request:\n" + r.toString());
                
                // Create a response for the request using a handler
                Response resp;
                try {
                    // Check if the request has the 'Host' header if it's a 1.1 connection.
                    if (r.getHeader().getVersion().equals("HTTP/1.1") && !r.getHeader().getHeaderMap().containsKey("Host")) {
                        resp = new Response("HTTP/1.1", 400, "Bad Request");
                    } else {
                        // Find proper handler for this request.
                        Handler h = null;
                        switch (r.getHeader().getCommand()) {
                            case "GET":
                                h = new GetHandler(getSocket(), r);
                                break;
                            case "HEAD":
                                h = new HeadHandler(getSocket(), r);
                                break;
                            case "PUT":
                                h = new PutHandler(getSocket(), r);
                                break;
                            case "POST":
                                h = new PostHandler(getSocket(), r);
                                break;
                            default:
                                System.out.println("Command not supported: " + r.getHeader().getCommand());
                                h = new NotImplementedHandler(getSocket(), r);
                                break;
                        }
                        // The handler creates a response
                        resp = h.handle();
                    }
                } catch (Exception e) { // Of course, if a closed connection was
                                        // the cause of the error, this won't
                                        // help.
                    resp = new Response(r.getHeader().getVersion(), 500, "Server Error");
                }
                // Send the response
                System.out.println("Sending response: " + resp.toString());
                getSocket().send(resp);
                
                // Close the connection of the 'Connection' header specified we should.
                // Yoda expression to make sure it works with a null.
                if (r.getHeader().getVersion().equals("HTTP/1.1") && "close".equals(r.getHeader().getHeaderField("Connection"))) {
                    getSocket().getSocket().close();
                    break;
                }
                // Close the connection if we're working with a 1.1 connection
                if (r.getHeader().getVersion().equals("HTTP/1.0")) {
                    getSocket().getSocket().close();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while handling a request.");
            System.out.println(e.getMessage());
            // e.printStackTrace();
        } catch (SocketClosedException e) {
            System.out.println("Socket has been closed, stopping worker here.");
            // e.printStackTrace();
        }
        
        System.out.println("Closing this worker.");
    }
    
}
