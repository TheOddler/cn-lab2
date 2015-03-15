package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

import cnlab2.common.URI;

/**
 * The handler for a PUT handler
 *
 * Basically a POST handler with a different command string.
 */
public class PUTHandler extends POSTHandler {

    public PUTHandler(Client client, URI uri) throws UnknownHostException, IOException {
        super(client, uri);
    }
    
    @Override
    public String getCommand() {
        return "PUT";
    }
    
}
