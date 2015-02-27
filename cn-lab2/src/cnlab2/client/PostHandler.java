package cnlab2.client;

public class PostHandler extends Handler{

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
		
		
		Response result = null;
		return result;
	}
	

}
