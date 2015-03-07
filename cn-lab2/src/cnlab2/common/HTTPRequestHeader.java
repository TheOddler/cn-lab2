package cnlab2.common;

import java.io.IOException;

public class HTTPRequestHeader extends HTTPHeader {
    private String command;
    private URI uri;
    
    public HTTPRequestHeader(String command, URI uri, String version) {
        super(version);
        setCommand(command);
        setUri(uri);
    }
    
    public HTTPRequestHeader(SmartSocket ss) throws IOException {
        super(ss);
        parseURI(ss);
    }
    
    @Override
    protected void parseFirstLine(SmartSocket ss) throws IOException {
        String statusLine = ss.readLine();
        
        int firstSpace = statusLine.indexOf(" ");
        String command = statusLine.substring(0, firstSpace);
        int secondSpace = statusLine.indexOf(" ", firstSpace + " ".length());
        String resourceStr = statusLine.substring(firstSpace + " ".length(), secondSpace);
        String versionStr = statusLine.substring(secondSpace + " ".length());
        
        setUri(new URI(DEFAULT_HOST, resourceStr));
        setVersion(versionStr);
        setCommand(command);
    }
    
    private void parseURI(SmartSocket ss) {
        String host = getHeaderMap().containsKey("Host") ? getHeaderMap().get("Host") : DEFAULT_HOST;
        int port = DEFAULT_PORT;
        URI uri = new URI(host, getUri().getResource());
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
