package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Client {

	public Client() {
	}

	protected boolean canHaveAsCommand(HTTPCommand command) {
		return command != null;
	}

	protected boolean canHaveAsURI(URI uri) {
		return uri != null;
	}

	protected boolean canHaveAsPort(int port) {
		return port >= 0 && port <= 65535;
	}

	public void handle(HTTPCommand command, URI uri, int port) throws UnknownHostException, IOException, IllegalArgumentException {
		if (!this.canHaveAsCommand(command))
			throw new IllegalArgumentException("Invalid command:" + command.toString());
		if (!this.canHaveAsURI(uri))
			throw new IllegalArgumentException("Invalid URI:" + uri.toString());
		if (!this.canHaveAsPort(port))
			throw new IllegalArgumentException("Invalid port: " + Integer.toString(port));
		
	}
	
	public abstract Handler getHandlerFor(String command, URI uri);
	public abstract Socket getSocketFor(URI uri) throws UnknownHostException, IOException;
	public abstract String getVersion();
}
