package cnlab2.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTTP10Client extends Client {

	public HTTP10Client() {
		super();
	}

	protected boolean canHaveAsCommand(HTTPCommand command) {
		if (!super.canHaveAsCommand(command)) {
			return false;
		} else {
			return command == HTTPCommand.GET
					|| command == HTTPCommand.HEAD
					|| command == HTTPCommand.POST;
		}

	}

	public void handle(HTTPCommand command, URI uri, int port) throws UnknownHostException, IOException, IllegalArgumentException {
		super.handle(command,uri,port);
		switch (command){
		case GET:
			this.get(uri, port);
			break;
		default:
			throw new IllegalArgumentException("Invalid command: " + command.toString());
		}
	}
	
	private void get(URI uri, int port) throws UnknownHostException, IOException{
		// Create a socket to localhost (this machine, port 6789).
		Socket clientSocket = new Socket(uri.getHost(), port);

		// Create outputstream (convenient data writer) to this host.
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());

		// Create an inputstream (convenient data reader) to this host
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));

		StringBuilder requestBuilder = new StringBuilder();
		requestBuilder.append("GET");
		requestBuilder.append(" ");
		requestBuilder.append(uri.getResource());
		requestBuilder.append(" ");
		requestBuilder.append("HTTP/1.0\n");
		requestBuilder.append("\n");
		String requestStr = requestBuilder.toString();
		System.out.println(requestStr);
		
		outToServer.writeBytes(requestStr);

		// Read text from the server and write it to the screen.
		while (true){
			String next = inFromServer.readLine();
			if (next == null)break;
			System.out.println(next);
		}
		

		// Close the socket.
		clientSocket.close();
		
	}

	public Socket getSocketFor(URI uri, int port) throws UnknownHostException, IOException {
		return new Socket(uri.getHost(), port);
	}
}
