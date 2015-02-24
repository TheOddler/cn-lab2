package cnlab2;

public abstract class HTTPClient {

	public static void main(String[] args) throws Exception {

		if (args.length != 4) {
			System.out.println("You idiot!");
		}

		String command = args[0];
		String uri = args[1];
		int port = Integer.parseInt(args[2]);

		HTTPClient client = null;

		if (args[3].equals("1.0")) {
			client = new HTTP10Client(HTTPCommand.GET,uri,port);
		} else if (args[3].equals("1.1")) {
			client = new HTTP11Client(HTTPCommand.GET,uri,port);
		} else {
			System.out.println("You done goofed!");
		}

		client.perform();
	}

	private HTTPCommand command;
	private String uri;
	private int port;

	public HTTPClient(HTTPCommand command, String uri, int port) {
		setCommand(command);
		setUri(uri);
		setPort(port);
	}

	private HTTPCommand getCommand() {
		return command;
	}
	
	private void setCommand(HTTPCommand command) {
		this.command = command;
	}

	public String getUri() {
		return uri;
	}

	private void setUri(String uri) {
		this.uri = uri;
	}

	public int getPort() {
		return port;
	}

	private void setPort(int port) {
		this.port = port;
	}

	public abstract void perform() throws Exception;
}
