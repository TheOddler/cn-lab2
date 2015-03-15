package cnlab2.client;


import cnlab2.common.Response;
import cnlab2.common.URI;

public abstract class CNLabTwoClient {
    
    public static void main(String[] args) throws Exception {
        
        if (args.length != 4) {
            System.out.println("Usage: Command Uri Port Version");
        }
        
        // Get the arguments
        String command = args[INDEX_COMMAND];
        int port = Integer.parseInt(args[INDEX_PORT]);
        URI uri = new URI(args[INDEX_URI], port);
        
        String version = args[INDEX_VERSION];
        
        // Get the proper client based on the version argument
        Client client = null;
        if (version.equals("1.0")) {
            client = new HTTP10Client();
        } else if (version.equals("1.1")) {
            client = new HTTP11Client();
        } else {
            throw new IllegalAccessException("Invalid version: " + version);
        }
        
        // Handler the command given in the arguments
        HTTPCommand comm = new HTTPCommand(command, uri);
        Response response = client.handle(comm);
        
        // If the argument was a GET, and we got a html page, then let our simple browser handle the rest.
        if (command.equals("GET") && response.getHeader().getHeaderField("Content-Type").contains("text/html")) {
            SimpleBrowser browser = new SimpleBrowser();
            browser.Browse(response, client);
        }
    }
    
    private static final int INDEX_COMMAND = 0;
    private static final int INDEX_URI = 1;
    private static final int INDEX_PORT = 2;
    private static final int INDEX_VERSION = 3;
}
