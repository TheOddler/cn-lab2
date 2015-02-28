package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cnlab2.common.Response;
import cnlab2.common.URI;


public class HTTP11Client extends Client{

    private final List<SmartSocket> knownSockets = new ArrayList<SmartSocket>();
    
	public HTTP11Client() {
		super();
	}
	
	public SmartSocket getSmartSocketFor(URI uri) throws UnknownHostException, IOException {
	    // check if we already made a smart socket for this uri
	    for (SmartSocket socket: knownSockets) {
	        // see if this socket can be used for this uri
	        if (socket.canBeUsedFor(uri)) {
	            // refresh smartsocket, reopening it if it was closed
                socket.RefreshIfNeeded();
                // if it's open, return it.
                return socket;
	        }
	    }
	    
	    SmartSocket newSocket = new SmartSocket(uri);
	    knownSockets.add(newSocket);
	    return newSocket;
	}
	
	@Override
	public void handle(String command, URI uri) throws UnknownHostException, IOException {
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
		case "DELETE":
		case "CONNECT":
			throw new IllegalArgumentException("Command not yet implemented");
		default:
			throw new IllegalArgumentException("Unknown command");
		}
		
		//TODO Pipelining
		try {
    		handler.send();
            handler.send();
    		
    		Handler secondHandler;
            
            secondHandler = new HEADHandler(this, new URI("http://www.google.be/?gfe_rd=cr&ei=BTnyVLiwF4WeOsCRgcgH", 80));
            secondHandler.send();
            
            Response response = handler.receive();
            System.out.print("Eerste: " + response);
            
            Response response2 = handler.receive();
            System.out.print("Eerste nog eens: " + response2);
            
            Response secondResponse = secondHandler.receive();
            System.out.print("Tweede: " + secondResponse);
        } catch (Exception e) {
            
        }
	}

	@Override
	public String getVersion() {
		return "HTTP/1.1";
	}

}
