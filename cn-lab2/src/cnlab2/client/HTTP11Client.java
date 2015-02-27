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
	public String getVersion() {
		return "HTTP/1.1";
	}

}
