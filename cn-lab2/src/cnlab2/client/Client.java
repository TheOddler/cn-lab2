package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.URI;

public abstract class Client {

	public Client() {
	}
	
	public abstract List<Response> handle(HTTPCommand... commands) throws UnknownHostException, IOException;
	public abstract SmartSocket getSmartSocketFor(URI uri) throws UnknownHostException, IOException;
	public abstract String getVersion();
}
