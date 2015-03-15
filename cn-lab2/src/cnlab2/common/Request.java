package cnlab2.common;

import java.io.IOException;

/**
 * A class representing a HTTP request from the client 
 *
 */
public class Request extends HTTPMessage {
    
    /**
     * Create a request by reading from a socket
     * @param ss
     * @throws IOException
     * @throws SocketClosedException
     */
    public Request(SmartSocket ss) throws IOException, SocketClosedException {
        setHeader(new HTTPRequestHeader(ss));
        readContent(ss);
    }
    
    /**
     * Create a request to be send
     * @param command The http command for this request
     * @param uri The uri the request will be send to
     * @param version The http version
     * @param content The text-content to be send
     */
    public Request(String command, URI uri, String version, String content) {
        setHeader(new HTTPRequestHeader(command, uri, version));
        setContent(content.getBytes());
    }
    
    /**
     * Create a request to be send
     * @param command The http command for this request
     * @param uri The uri this request will be send to
     * @param version The http version
     * @param content The content as bytes
     */
    public Request(String command, URI uri, String version, byte[] content) {
        setHeader(new HTTPRequestHeader(command, uri, version));
        setContent(content);
    }
    
    @Override
    public HTTPRequestHeader getHeader() {
        return (HTTPRequestHeader) super.getHeader();
    }
    
    private void setHeader(HTTPRequestHeader header) {
        super.setHeader(header);
    }
    
    @Override
    public String toString() {
        StringBuilder messageBuilder = new StringBuilder();
        
        messageBuilder.append(getHeader());
        messageBuilder.append("\r\n");
        
        if (getContent() != null) {
            messageBuilder.append(getContentString());
        }
        
        return messageBuilder.toString();
    }
    
}
