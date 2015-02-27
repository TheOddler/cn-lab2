package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class HTTP11Client extends Client{

	public HTTP11Client() {
		super();
	}
	
	public Socket getSocketFor(URI uri) throws UnknownHostException, IOException {
		return new Socket(uri.getHost(), uri.getPort());
		// Dummy needs proper implementation.
	}
	
	@Override
	public Handler getHandlerFor(String command, URI uri) {
		switch (command) {
		case "GET":
			return new GETHandler(this, uri);
		case "HEAD":
			return new HEADHandler(this, uri);
		case "POST":
			return new PostHandler(this, uri);
		case "OPTIONS":
		case "PUT":
		case "DELETE":
		case "TRACE":
		case "CONNECT":
			throw new IllegalArgumentException("Command not yet implemented");
		default:
			throw new IllegalArgumentException("Unknown command");
		}
	}

	@Override
	public String getVersion() {
		return "HTTP/1.1";
	}

}
