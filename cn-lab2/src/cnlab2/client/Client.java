package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

import cnlab2.common.SmartSocket;
import cnlab2.common.URI;

public abstract class Client {

	public Client() {
	}
	
	public abstract void handle(String command, URI uri) throws UnknownHostException, IOException;
	public abstract SmartSocket getSmartSocketFor(URI uri) throws UnknownHostException, IOException;
	public abstract String getVersion();
}
