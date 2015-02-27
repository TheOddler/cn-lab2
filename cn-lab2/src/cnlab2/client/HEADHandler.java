package cnlab2.client;

import java.net.Socket;

public class HEADHandler extends Handler {

	public HEADHandler(Client client, URI uri) {
		super(client, uri);
	}

	@Override
	public Response handle() {
		GETHandler g = new GETHandler();
		Response r = g.handle();
		Response head = new Response(r.getHeader());
		return head;
	}

}
