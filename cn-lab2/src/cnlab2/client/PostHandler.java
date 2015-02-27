package cnlab2.client;

import java.util.Scanner;

public class PostHandler extends Handler {

	public PostHandler(Client client, URI uri) {
		super(client, uri);
	}

	@Override
	public Response handle() {
		StringBuilder requestBuilder = new StringBuilder();
		requestBuilder.append("POST");
		requestBuilder.append(" ");
		requestBuilder.append(getUri().getResource());
		requestBuilder.append(" ");
		requestBuilder.append(getClient().getVersion());
		requestBuilder.append("\n");
		String requestStr = requestBuilder.toString();

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
		
		Response result = new Response(contentBuilder.toString());
		return result;
	}

}
