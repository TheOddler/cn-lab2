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
		Socket socket = getClient().getSocketFor(getUri(), 80);
		
		sendString(socket, getRequestString());
		
		return getResponse(socket);
	}
	
	String getRequestString() {
		StringBuilder requestBuilder = new StringBuilder();
		requestBuilder.append("GET");
		requestBuilder.append(" ");
		requestBuilder.append(getUri().getResource());
		requestBuilder.append(" ");
		requestBuilder.append("HTTP/1.0\n");
		requestBuilder.append("\n");
		return requestBuilder.toString();
	}

}
