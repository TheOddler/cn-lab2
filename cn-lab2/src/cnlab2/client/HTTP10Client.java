package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.URI;

public class HTTP10Client extends Client {
    
    public HTTP10Client() {
        super();
    }
    
    // Simply return a new socket for every request.
    @Override
    public SmartSocket getSmartSocketFor(URI uri) throws UnknownHostException, IOException {
        return new SmartSocket(uri);
    }
    
    // Handle a single http1.0 command.
    @Override
    public List<Response> handle(List<HTTPCommand> commands) throws UnknownHostException, IOException {
        List<Response> responses = new ArrayList<Response>();
        
        for (HTTPCommand command : commands) {
            responses.add(handleOne(command.getCommand(), command.getUri()));
        }
        
        return responses;
    }
    
    public Response handleOne(String command, URI uri) throws UnknownHostException, IOException {
        Handler handler;
        
        // Create a proper handler based on the command.
        switch (command) {
            case "GET":
                handler = new GETHandler(this, uri);
                break;
            case "HEAD":
                handler = new HEADHandler(this, uri);
                break;
            case "POST":
                handler = new POSTHandler(this, uri);
                break;
            default:
                throw new IllegalArgumentException("Unknown command");
        }
        
        // Let the handler send its request.
        handler.send();
        // Receive the response.
        Response response = handler.receive();
        
        // Close the connection.
        handler.getSmartSocket().getSocket().close();
        
        return response;
    }
    
    @Override
    public String getVersion() {
        return "HTTP/1.0";
    }
    
}
