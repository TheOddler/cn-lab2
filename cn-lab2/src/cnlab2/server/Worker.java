package cnlab2.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Worker implements Runnable {

	private Socket socket;

	public Worker(Socket socket) {
		setSocket(socket);
	}

	public Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try{
	      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
	      DataOutputStream outToClient = new DataOutputStream(getSocket().getOutputStream());
	      
	      

	      // Read text from the client, make it uppercase and write it back.
	      String clientSentence = inFromClient.readLine();
	      System.out.println("Received: " + clientSentence);
	      String capsSentence = clientSentence.toUpperCase() + '\n';
	      outToClient.writeBytes(capsSentence);
		}catch (IOException e){
			System.out.println("Something went wrong while handling a request.");
			e.printStackTrace();
		}
	}
}
