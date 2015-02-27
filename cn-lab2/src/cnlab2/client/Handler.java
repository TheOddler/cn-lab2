package cnlab2.client;

import java.net.Socket;

public abstract class Handler {
	public abstract void handle(Socket socket);
}
