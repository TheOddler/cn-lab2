package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HEADHandler extends Handler {

	public HEADHandler(Client client, URI uri) {
		super(client, uri);
	}
	
	@Override
	public Response handle() throws UnknownHostException, IOException {
		Socket socket = getClient().getSocketFor(getUri(), 80);
		
		sendString(socket, getRequestString("HEAD"));
		
		return getResponse(socket);
	}

}
