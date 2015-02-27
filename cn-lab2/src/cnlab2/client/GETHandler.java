package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class GETHandler extends Handler {
	
	public GETHandler(Client client, URI uri) {
		super(client, uri);
	}
	
	@Override
	public Response handle() throws UnknownHostException, IOException {
		Socket socket = getClient().getSocketFor(getUri(), getPort());
		
		sendString(socket, getRequestString("GET"));
		
		return getResponse(socket);
	}
	
}
