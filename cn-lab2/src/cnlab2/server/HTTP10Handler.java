package cnlab2.server;

import java.net.Socket;

import cnlab2.common.Request;

public class HTTP10Handler extends Handler{
	
	public HTTP10Handler(Socket socket) {
		super(socket);
	}

}
