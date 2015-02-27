package cnlab2.client;

import java.net.Socket;

public abstract class Handler {

	private Client client;
	private URI uri;
	
	
	public Handler(Client client, URI uri){
		setClient(client);
		setUri(uri);
	}
	
	public abstract Response handle();

	public Client getClient() {
		return client;
	}

	private void setClient(Client client) {
		this.client = client;
	}

	public URI getUri() {
		return uri;
	}

	private void setUri(URI uri) {
		this.uri = uri;
	}
}
