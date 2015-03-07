package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

import cnlab2.common.ContentlessResponse;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.URI;

public class HEADHandler extends Handler {
    
    public HEADHandler(Client client, URI uri) throws UnknownHostException, IOException {
        super(client, uri);
    }
    
    @Override
    public String getCommand() {
        return "HEAD";
    }
    
    protected Response getResponse(SmartSocket smartSocket) throws IOException {
        Response resp = new ContentlessResponse(smartSocket);
        
        System.out.println("Response (" + getCommand() + "):\n" + resp + "\nEnd resp\n");
        
        return resp;
    }
}
