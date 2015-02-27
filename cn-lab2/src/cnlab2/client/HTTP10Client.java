package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTTP10Client extends Client {

	public HTTP10Client() {
		super();
	}

	@Override
	public Socket getSocketFor(URI uri) throws UnknownHostException, IOException {
		return new Socket(uri.getHost(), uri.getPort());
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
		default:
			throw new IllegalArgumentException("Unknown command");
		}
	}

	@Override
	public String getVersion() {
		return "HTTP/1.0";
	}
	
}
