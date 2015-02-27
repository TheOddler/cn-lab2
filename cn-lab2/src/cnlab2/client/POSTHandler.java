package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import cnlab2.common.Response;

public class POSTHandler extends Handler {

	public POSTHandler(Client client, URI uri) {
		super(client, uri);
	}

	@Override
	public String getCommand() {
		return "POST";
	}
	
	@Override
	public Response handle() throws UnknownHostException, IOException {
		String r = getRequestString(getCommand());
		
		Socket socket = getClient().getSocketFor(getUri());
		
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
		
		sc.close();
		
		Response result = new Response(contentBuilder.toString());
		return result;
	}

}
