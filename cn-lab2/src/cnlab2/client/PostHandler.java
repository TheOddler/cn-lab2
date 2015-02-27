package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class PostHandler extends Handler {

	public PostHandler(Client client, URI uri) {
		super(client, uri);
	}

	@Override
	public Response handle() throws UnknownHostException, IOException {
		String r = getRequestString("POST");
		
		Socket socket = getClient().getSocketFor(getUri(), 80);
		
		Scanner sc = new Scanner(System.in);
		StringBuilder contentBuilder = new StringBuilder();
		String previous = "";
		String current = "";
		while (true) {
			current = sc.nextLine();
			if (previous.equals("") && current.equals(""))
				break;
			contentBuilder.append(current + "\n");
			previous = current;
		}
		
		sendString(socket, r);
		
		Response result = new Response(contentBuilder.toString());
		return result;
	}

}
