package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.SocketClosedException;
import cnlab2.common.URI;


public class HTTP11Client extends Client{

    private final List<SmartSocket> knownSockets = new ArrayList<SmartSocket>();
    
	public HTTP11Client() {
		super();
	}

	// Returns the same SmartSocket if the uri host:port are the same.
    @Override
	public SmartSocket getSmartSocketFor(URI uri) throws UnknownHostException, IOException {
	    // check if we already made a smart socket for this uri
	    for (SmartSocket socket: knownSockets) {
	        // see if this socket can be used for this uri
	        if (socket.canBeUsedFor(uri)) {
	            System.out.println("Re-using socket: " + socket.toString()
	                    + " for " + uri.toString());
	            // refresh smartsocket, reopening it if it was closed
                socket.RefreshIfNeeded();
                // if it's open, return it.
                return socket;
	        }
	    }
	    
	    System.out.println("No socket to reuse, creation new one for " + uri.toString());
	    
	    SmartSocket newSocket = new SmartSocket(uri);
	    knownSockets.add(newSocket);
	    return newSocket;
	}
	
    
	@Override
	public List<Response> handle(List<HTTPCommand> commands) throws UnknownHostException, IOException, SocketClosedException {
		
	    List<Handler> handlers = new ArrayList<Handler>();
	    
	    for (HTTPCommand command: commands) {
	        handlers.add(getHandlerFor(command.getCommand(), command.getUri()));
	    }
	    
	    // Allows for pipelining if multiple requests are send through the same socket.
	    for(Handler handler: handlers) {
	        handler.send();
	    }
	    
	    List<Response> responses = new ArrayList<Response>();
	    
	    for(Handler handler: handlers) {
            responses.add(handler.receive());
        }
	    
	    return responses;
	}
	
	private Handler getHandlerFor(String command, URI uri) throws UnknownHostException, IOException {
	    Handler handler;
        
        switch (command) {
        case "GET":
            handler =  new GETHandler(this, uri);
            break;
        case "HEAD":
            handler =  new HEADHandler(this, uri);
            break;
        case "POST":
            handler =  new POSTHandler(this, uri);
            break;
        case "TRACE":
            handler =  new TRACEHandler(this, uri);
            break;
        case "OPTIONS":
            handler =  new OPTIONSHandler(this, uri);
            break;
        case "PUT":
            handler =  new PUTHandler(this, uri);
            break;
        case "DELETE":
        case "CONNECT":
            throw new IllegalArgumentException("Command not yet implemented");
        default:
            throw new IllegalArgumentException("Unknown command");
        }
        
        return handler;
	}

	@Override
	public String getVersion() {
		return "HTTP/1.1";
	}

}
