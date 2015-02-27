package cnlab2.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private int port;
	public Server(int port){
		setPort(port);
	}
	
	public void start() throws IOException{
	    ServerSocket welcomeSocket = new ServerSocket(getPort());
	    
	    while(true)
	      {
	      Socket connectionSocket = welcomeSocket.accept();
	  
	      BufferedReader inFromClient = new BufferedReader(new InputStreamReader	(connectionSocket.getInputStream()));
	      DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

	      // Read text from the client, make it uppercase and write it back.
	      String clientSentence = inFromClient.readLine();
	      System.out.println("Received: " + clientSentence);
	      String capsSentence = clientSentence.toUpperCase() + '\n';
	      outToClient.writeBytes(capsSentence);
	      System.out.println("Sent: "+ capsSentence);
	      }

	}

	public int getPort() {
		return port;
	}

	private void setPort(int port) {
		this.port = port;
	}
	
}
