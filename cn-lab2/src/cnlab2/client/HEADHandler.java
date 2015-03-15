package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.SocketClosedException;
import cnlab2.common.URI;

/**
 * The handler for a HEAD request
 *
 */
public class HEADHandler extends Handler {
    
    public HEADHandler(Client client, URI uri) throws UnknownHostException, IOException {
        super(client, uri);
    }
    
    @Override
    public String getCommand() {
        return "HEAD";
    }
    
    /**
     * The HEAD request is a little special in the response it gets.
     */
    @Override
    protected Response getResponse(SmartSocket smartSocket) throws IOException, SocketClosedException {
        System.out.println("\nReceiving response:");
        // The response won't content, even though the headers will still have a content-length entry
        Response resp = new ContentlessResponse(smartSocket);
        System.out.println(resp.toString());
        System.out.println("\nFinished receiving response:");
        return resp;
    }
}
