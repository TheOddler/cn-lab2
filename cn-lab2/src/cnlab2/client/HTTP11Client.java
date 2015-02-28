package cnlab2.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import cnlab2.common.Response;
import cnlab2.common.URI;


public class HTTP11Client extends Client{

	public HTTP11Client() {
		super();
	}
	
	public Socket getSocketFor(URI uri) throws UnknownHostException, IOException {
		return new Socket(uri.getHost(), uri.getPort());
		// Dummy needs proper implementation.
	}
	
	@Override
	public void handle(String command, URI uri) throws UnknownHostException, IOException {
		Handler handler;
		
		switch (command) {
		case "GET":
			handler =  new GETHandler(this, uri);
			break;
		case "HEAD":
			handler =  new HEADHandler(this, uri);
			break;
		case "POST":
			handler =  new POSTHandler(this, uri);
			break;
		case "TRACE":
			handler =  new TRACEHandler(this, uri);
			break;
		case "OPTIONS":
			handler =  new OPTIONSHandler(this, uri);
			break;
		case "PUT":
		case "DELETE":
		case "CONNECT":
			throw new IllegalArgumentException("Command not yet implemented");
		default:
			throw new IllegalArgumentException("Unknown command");
		}
		
		//TODO Pipelining
		handler.send();
		
		Handler secondHandler = new GETHandler(this, uri);
		secondHandler.send();
		
		Response response = handler.receive();
		System.out.print("Eerste: " + response);
		
		Response response2 = secondHandler.receive();
		System.out.print("Tweede: " + response2);
	}

	@Override
	public String getVersion() {
		return "HTTP/1.1";
	}

}
