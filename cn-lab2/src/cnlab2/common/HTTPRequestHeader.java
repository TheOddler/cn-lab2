package cnlab2.common;

import java.io.BufferedReader;
import java.io.IOException;

public class HTTPRequestHeader extends HTTPHeader {
    private String command;
    private URI uri;
    
    public HTTPRequestHeader(String command, URI uri, String version) {
        super(version);
        setCommand(command);
        setUri(uri);
    }
    
    public HTTPRequestHeader(BufferedReader in) throws IOException {
        super(in);
        parseURI(in);
    }
    
    @Override
    protected void parseFirstLine(BufferedReader in) throws IOException {
        String statusLine = in.readLine();
        
        int firstSpace = statusLine.indexOf(" ");
        String command = statusLine.substring(0, firstSpace);
        int secondSpace = statusLine.indexOf(" ", firstSpace + " ".length());
        String resourceStr = statusLine.substring(firstSpace + " ".length(), secondSpace);
        String versionStr = statusLine.substring(secondSpace + " ".length());
        
        setUri(new URI(DEFAULT_HOST, resourceStr));
        setVersion(versionStr);
        setCommand(command);
    }
    
    private void parseURI(BufferedReader in) {
        String host = getHeaderMap().containsKey("Host") ? getHeaderMap().get("Host") : DEFAULT_HOST;
        int port = DEFAULT_PORT;
        URI uri = new URI(host, getUri().getResource());
        setUri(uri);
    }
    
    @Override
    public String toString() {
        StringBuilder headerBuilder = new StringBuilder();
        
        headerBuilder.append(command);
        headerBuilder.append(" ");
        headerBuilder.append(getUri().getResource());
        headerBuilder.append(" ");
        headerBuilder.append(getVersion());
        headerBuilder.append("\n");
        for (String key : getHeaderMap().keySet()) {
            headerBuilder.append(key);
            headerBuilder.append(": ");
            headerBuilder.append(getHeaderMap().get(key));
            headerBuilder.append("\n");
        }
        
        return headerBuilder.toString();
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
}
