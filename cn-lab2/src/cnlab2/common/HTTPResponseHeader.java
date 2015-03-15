package cnlab2.common;

import java.io.IOException;

/**
 * A class representing the headers of a response
 * Contains both the first line of a response as the other headers.
 */
public class HTTPResponseHeader extends HTTPHeader {
    
    private int status;
    private String message;
    
    public HTTPResponseHeader(String message, int status, String version) {
        super(version);
        setMessage(message);
        setStatus(status);
    }
    
    /**
     * Create a header by reading from a socket
     * @param ss
     * @throws IOException
     * @throws SocketClosedException
     */
    public HTTPResponseHeader(SmartSocket ss) throws IOException, SocketClosedException {
        super(ss);
    }
    
    /**
     * Parese the first line of the headers
     */
    @Override
    protected void parseFirstLine(SmartSocket ss) throws IOException, SocketClosedException {
        String statusLine = ss.readLine(); // lines[0]
        
        int firstSpace = statusLine.indexOf(" ");
        String version = statusLine.substring(0, firstSpace);
        int secondSpace = statusLine.indexOf(" ", firstSpace + " ".length());
        String statusCodeStr = statusLine.substring(firstSpace + " ".length(), secondSpace);
        String statusMessageStr = statusLine.substring(secondSpace + " ".length());
        
        setVersion(version);
        setStatus(Integer.parseInt(statusCodeStr));
        setMessage(statusMessageStr);
    }
    
    @Override
    protected String getFirstLine() {
        return getVersion() + " " + getStatus() + " " + getMessage() + "\r\n";
    }
    
    public int getStatus() {
        return status;
    }
    
    private void setStatus(int status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    private void setMessage(String message) {
        this.message = message;
    }
    
}
