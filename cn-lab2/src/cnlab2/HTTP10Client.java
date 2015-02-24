package cnlab2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class HTTP10Client extends HTTPClient {

	public HTTP10Client(HTTPCommand command, String uri, int port) {
		super(command, uri, port);
	}

	public void perform() throws Exception {
		// Create a socket to localhost (this machine, port 6789).
		Socket clientSocket = new Socket(this.getUri(), this.getPort());

		// Create outputstream (convenient data writer) to this host.
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());

		// Create an inputstream (convenient data reader) to this host
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));

		outToServer.writeBytes("GET /index.html HTTP/1.0" + "\n" + "From: someuser@jmarshall.com" + "\n" + "User-Agent: HTTPTool/1.0" +"\n\n");

		// Read text from the server and write it to the screen.
		String response = inFromServer.readLine();
		System.out.println("FROM SERVER: " + response);

		// Close the socket.
		clientSocket.close();

	}
}
