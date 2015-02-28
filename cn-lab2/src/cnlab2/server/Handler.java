package cnlab2.server;

import java.net.Socket;


public abstract class Handler  {
	private Socket socket;

	public Handler(Socket socket) {
		setSocket(socket);
	}

	public Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket socket) {
		this.socket = socket;
	}

}
