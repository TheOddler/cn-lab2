package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

import cnlab2.common.URI;

public class HEADHandler extends Handler {

	public HEADHandler(Client client, URI uri) throws UnknownHostException, IOException {
		super(client, uri);
	}
	
	@Override
	public String getCommand() {
		return "HEAD";
	}

}
