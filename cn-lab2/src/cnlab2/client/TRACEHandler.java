package cnlab2.client;

public class TRACEHandler extends Handler {

	public TRACEHandler(Client client, URI uri) {
		super(client, uri);
	}
	
	@Override
	public String getCommand() {
		return "TRACE";
	}
	
}
