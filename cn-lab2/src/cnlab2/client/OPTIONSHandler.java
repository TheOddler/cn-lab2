package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.SocketClosedException;
import cnlab2.common.URI;

/**
 * The handler for OPTIONS requests
 * 
 * Similar to the head handler only with a different command.
 *
 */
public class OPTIONSHandler extends Handler {

	public OPTIONSHandler(Client client, URI uri) throws UnknownHostException, IOException {
		super(client, uri);
	}
	
	@Override
	public String getCommand() {
		return "OPTIONS";
	}
	
	@Override
    protected Response getResponse(SmartSocket smartSocket) throws IOException, SocketClosedException {
        System.out.println("\nReceiving response:");
        Response resp = new ContentlessResponse(smartSocket);
        System.out.println(resp.toString());
        System.out.println("\nFinished receiving response:");
        return resp;
    }

}
