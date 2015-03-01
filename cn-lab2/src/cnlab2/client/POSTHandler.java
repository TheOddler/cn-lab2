package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import cnlab2.common.Request;
import cnlab2.common.URI;

public class POSTHandler extends Handler {

	public POSTHandler(Client client, URI uri) throws UnknownHostException, IOException {
		super(client, uri);
	}

	@Override
	public String getCommand() {
		return "POST";
	}
	
	@Override
	public void send() throws IOException {
		
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
		String content = contentBuilder.toString();
		
		Request r = new Request(getCommand(),getUri(),getClient().getVersion(),content);
		
		sendRequest(getSmartSocket(), r);
		
		sc.close();
	}

}
