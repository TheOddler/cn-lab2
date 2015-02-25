package cnlab2.client;

public enum HTTPCommand {
	GET, POST, HEAD,PUT, DELETE, OPTIONS, TRACE;
	
	public static HTTPCommand parseCommand(String commandstr){
		switch (commandstr)
		{
		case "GET":
			return GET;
		case "POST":
			return POST;
		case "HEAD":
			return HEAD;
		case "PUT":
			return PUT;
		case "DELETE":
			return DELETE;
		case "OPTIONS":
			return OPTIONS;
		case "TRACE":
			return TRACE;
		default:
			throw new IllegalArgumentException("You're an idiot");
		}
	}
}
