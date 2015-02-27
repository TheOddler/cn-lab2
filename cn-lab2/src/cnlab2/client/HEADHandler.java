package cnlab2.client;

import java.net.Socket;

public class HEADHandler extends Handler {

	@Override
	public Response handle(Socket socket) {
		GETHandler g = new GETHandler(socket);
		Response r = g.handle(socket);
		
			
		return null;
	}

}
