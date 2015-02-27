package cnlab2.client;

public class GETHandler extends Handler {
	
	public GETHandler(Client client, URI uri) {
		super(client, uri);
	}
	
	@Override
	public String getCommand() {
		return "GET";
	}
	
}
