package cnlab2.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketInfo {
	private Socket socket;
	private BufferedReader inFromServer;
	private DataOutputStream outToServer;
	
	public SocketInfo(Socket socket) throws IOException {
		this.socket = socket;
		
		inFromServer = new BufferedReader(
			new InputStreamReader(
					this.socket.getInputStream()
				)
			);
		outToServer = new DataOutputStream(this.socket.getOutputStream());
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getInFromServer() {
		return inFromServer;
	}

	public void setInFromServer(BufferedReader inFromServer) {
		this.inFromServer = inFromServer;
	}

	public DataOutputStream getOutToServer() {
		return outToServer;
	}

	public void setOutToServer(DataOutputStream outToServer) {
		this.outToServer = outToServer;
	}
}
