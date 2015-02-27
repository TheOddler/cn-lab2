package cnlab2.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public abstract class Handler {

	private Client client;
	private URI uri;

	public Handler(Client client, URI uri) {
		setClient(client);
		setUri(uri);
	}

	public abstract Response handle();

	public Response getResponse(Socket socket) throws IOException {
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		StringBuilder responseBuilder = new StringBuilder();
		while (true) {
			String next = inFromServer.readLine();
			if (next == null) break;
			responseBuilder.append(next);
		}

		return new Response(responseBuilder.toString());
	}

	public void sendString(Socket socket, String str) throws IOException {
		DataOutputStream outToServer;
		outToServer = new DataOutputStream(socket.getOutputStream());
		outToServer.writeBytes(str);

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
