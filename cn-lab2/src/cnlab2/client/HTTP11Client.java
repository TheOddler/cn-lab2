package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class HTTP11Client extends Client{

	public HTTP11Client() {
		super();
	}
	
	public Socket getSocketFor(URI uri, int port) throws UnknownHostException, IOException {
		return new Socket(uri.getHost(), port);
		// Dummy needs proper implementation.
	}

}
