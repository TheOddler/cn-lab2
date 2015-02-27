package cnlab2.client;

public abstract class StupidClient {

	public static void main(String[] args) throws Exception {

		if (args.length != 4) {
			System.out.println("Usage: Command Uri Port Version");
		}

		HTTPCommand command = HTTPCommand.parseCommand(args[INDEX_COMMAND]);
		int port = Integer.parseInt(args[INDEX_PORT]);
		URI uri = new URI(args[INDEX_URI], port);
		
		String version = args[INDEX_VERSION];
		Client client = null;
		if (version.equals("1.0")) {
			client = new HTTP10Client();
		} else if (version.equals("1.1")) {
			client = new HTTP11Client();
		} else {
			throw new IllegalAccessException("Invalid version: " + version);
		}
		
		client.handle(command, uri, port);

	}
	
	private static final int INDEX_COMMAND = 0;
	private static final int INDEX_URI= 1;
	private static final int INDEX_PORT = 2;
	private static final int INDEX_VERSION= 3;
}
