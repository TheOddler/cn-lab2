package cnlab2.common;

import java.io.IOException;

/**
 * A class representing the headers of a http request.
 *
 */
public class HTTPRequestHeader extends HTTPHeader {
    private String command;
    private URI uri;
    
    public HTTPRequestHeader(String command, URI uri, String version) {
        super(version);
        setCommand(command);
        setUri(uri);
    }
    
    /**
     * Create the headers by reading from a socket
     * @param ss
     * @throws IOException
     * @throws SocketClosedException
     */
    public HTTPRequestHeader(SmartSocket ss) throws IOException, SocketClosedException {
        super(ss);
        parseURI();
    }
    
    /**
     * Read the first line of a request from a socket
     */
    @Override
    protected void parseFirstLine(SmartSocket ss) throws IOException, SocketClosedException {
        
        String statusLine = ss.readLine();
        
        System.out.println("Parsing: " + statusLine);
        int firstSpace = statusLine.indexOf(" ");
        String command = statusLine.substring(0, firstSpace);
        int secondSpace = statusLine.indexOf(" ", firstSpace + " ".length());
        String resourceStr = statusLine.substring(firstSpace + " ".length(), secondSpace);
        String versionStr = statusLine.substring(secondSpace + " ".length());
        
        setUri(new URI(DEFAULT_HOST, resourceStr));
        setVersion(versionStr);
        setCommand(command);
    }
    
    /**
     * Parse the URI if a host header is present
     */
    private void parseURI() {
        String host = getHeaderMap().containsKey("Host") ? getHeaderMap().get("Host") : DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (host.contains(":")) {
            int i = host.indexOf(":");
            port = Integer.parseInt(host.substring(i+1));
            host = host.substring(0,i);
        }
        URI uri = new URI(host, getUri().getResource(), port);
        setUri(uri);
    }
    
    public URI getUri() {
        return uri;
    }
    
    private void setUri(URI uri) {
        this.uri = uri;
    }
    
    public String getCommand() {
        return command;
    }
    
    protected void setCommand(String command) {
        this.command = command;
    }
    
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 80;
    
    @Override
    protected String getFirstLine() {
        return getCommand() + " " + getUri().getResource() + " " + getVersion() + "\r\n";
    }
}
