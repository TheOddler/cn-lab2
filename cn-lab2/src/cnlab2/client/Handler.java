package cnlab2.client;

import java.net.Socket;

public abstract class Handler {

	private Client client;
	
	public Handler(Client client){
		setClient(client);
	}
	
	public abstract Response handle(URI uri);

	public Client getClient() {
		return client;
	}

	private void setClient(Client client) {
		this.client = client;
	}
}
