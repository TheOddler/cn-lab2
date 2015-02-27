package cnlab2.client;

public class HEADHandler extends Handler {

	public HEADHandler(Client client, URI uri) {
		super(client, uri);
	}
	
	@Override
	public String getCommand() {
		return "HEAD";
	}

}
