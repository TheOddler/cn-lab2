package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Client {

	public Client() {
	}
	
	public abstract Handler getHandlerFor(String command, URI uri);
	public abstract Socket getSocketFor(URI uri) throws UnknownHostException, IOException;
	public abstract String getVersion();
}
