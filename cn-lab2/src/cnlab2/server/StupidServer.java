package cnlab2.server;

import java.io.IOException;

public class StupidServer {
	public static void main(String[] args) throws IOException {
		int port = Integer.parseInt(args[0]);
		Server s = new Server(port);
		s.start();
	}
}
