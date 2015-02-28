package cnlab2.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import cnlab2.common.Request;
import cnlab2.common.Response;

public abstract class Handler {

	private Client client;
	private URI uri;

	public Handler(Client client, URI uri) {
		setClient(client);
		setUri(uri);
	}
	
	public abstract String getCommand();
	public Response handle() throws UnknownHostException, IOException {
		Socket socket = getClient().getSocketFor(getUri());
		
		Request request = new Request(getCommand(), getUri(), client.getVersion(), "");
		sendRequest(socket,request);
		
		return getResponse(socket);
	}
	
	public Response getResponse(Socket socket) throws IOException {
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		StringBuilder responseBuilder = new StringBuilder();
		while (true) {
			String next = inFromServer.readLine();
			if (next == null) break;
			responseBuilder.append(next);
			responseBuilder.append("\n");
		}

		return new Response(responseBuilder.toString());
	}

	public void sendRequest(Socket socket, Request req) throws IOException {
		DataOutputStream outToServer;
		outToServer = new DataOutputStream(socket.getOutputStream());
		outToServer.writeBytes(req.toString());

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
