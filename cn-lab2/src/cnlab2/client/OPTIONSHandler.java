package cnlab2.client;

public class OPTIONSHandler extends Handler {

	public OPTIONSHandler(Client client, URI uri) {
		super(client, uri);
	}
	
	@Override
	public String getCommand() {
		return "OPTIONS";
	}

}
