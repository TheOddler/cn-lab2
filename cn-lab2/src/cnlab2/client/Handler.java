package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.URI;

public abstract class Handler {

	private Client client;
	private URI uri;
	private SmartSocket smartSocket;

	public Handler(Client client, URI uri) throws UnknownHostException, IOException {
		setClient(client);
		setUri(uri);
		
		smartSocket = getClient().getSmartSocketFor(getUri());
	}
	
	public abstract String getCommand();
	
	public void send() throws IOException {
		Request request = new Request(getCommand(), getUri(), client.getVersion(), "");
		sendRequest(smartSocket, request);
		
		System.out.println(request);
	}
	
	protected void sendRequest(SmartSocket smartSocket, Request req) throws IOException {
		smartSocket.getOut().writeBytes(req.toString());
	}
	
	public Response receive() throws IOException {
		return getResponse(smartSocket);
	}
	
	protected Response getResponse(SmartSocket smartSocket) throws IOException {
		return new Response(smartSocket.getIn());
	}
	
	
	
	public Client getClient() {
		return client;
	}

	private void setClient(Client client) {
		this.client = client;
	}

	public URI getUri() {
		return uri;
	}

	private void setUri(URI uri) {
		this.uri = uri;
	}

}
