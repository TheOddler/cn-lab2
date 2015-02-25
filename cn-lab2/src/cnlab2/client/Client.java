package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class Client {

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
			throw new IllegalArgumentException("Can't handle your shit");
		if (!this.canHaveAsURI(uri))
			throw new IllegalArgumentException("Excuse you! where?");
		if (!this.canHaveAsPort(port))
			throw new IllegalArgumentException("You want me to stick it where?");
		
	}
}
