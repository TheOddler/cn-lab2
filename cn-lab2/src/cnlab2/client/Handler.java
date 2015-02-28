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
	private Socket socket;

	public Handler(Client client, URI uri) throws UnknownHostException, IOException {
		setClient(client);
		setUri(uri);
		
		socket = getClient().getSocketFor(getUri());
	}
	
	public abstract String getCommand();
	
	public void send() throws IOException {
		Request request = new Request(getCommand(), getUri(), client.getVersion(), "");
		sendRequest(socket,request);
		
		System.out.println(request);
	}
	
	protected void sendRequest(Socket socket, Request req) throws IOException {
		DataOutputStream outToServer;
		outToServer = new DataOutputStream(socket.getOutputStream());
		outToServer.writeBytes(req.toString());
	}
	
	public Response receive() throws IOException {
		return getResponse(socket);
	}
	
	protected Response getResponse(Socket socket) throws IOException {
		BufferedReader inFromServer = new BufferedReader(
			new InputStreamReader(
					socket.getInputStream()
				)
			);

		/*StringBuilder responseBuilder = new StringBuilder();
		while (true) {
			String next = inFromServer.readLine();
			if (next == null) break;
			responseBuilder.append(next);
			responseBuilder.append("\n");
		}

		return new Response(responseBuilder.toString());*/
		
		return Response.ReadFirstResponse(inFromServer);
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
