package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.SocketClosedException;
import cnlab2.common.URI;

/**
 * The abstract representation of an http client
 *
 */
public abstract class Client {

	public Client() {
	}
	
	/**
	 * Handler a single command
	 * @param command The command
	 */
	public Response handle(HTTPCommand command) throws UnknownHostException, IOException, SocketClosedException {
	    return handle(Arrays.asList(command)).get(0);
	}
	/**
	 * Handle several commands
	 * @param commands The commands
	 */
	public abstract List<Response> handle(List<HTTPCommand> commands) throws UnknownHostException, IOException, SocketClosedException;
	public abstract SmartSocket getSmartSocketFor(URI uri) throws UnknownHostException, IOException;
	public abstract String getVersion();
}
