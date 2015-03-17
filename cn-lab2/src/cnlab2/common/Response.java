package cnlab2.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A class representing an HTTP response from the server.
 */
public class Response extends HTTPMessage {
    private URI uri;
    
    protected Response() {
    }
    
    /**
     * Create a response by reading incoming data from a socket.
     * 
     * @param ss
     *            The socket to read from
     * @param uri
     *            The URI the request to which this response is responding to
     *            was send to
     * @throws IOException
     * @throws SocketClosedException
     */
    public Response(SmartSocket ss, URI uri) throws IOException, SocketClosedException {
        setHeader(new HTTPResponseHeader(ss));
        setUri(uri);
        
        // Only read content if it was a successful response
        if (getHeader().getFirstLine().contains("200")) {
            readContent(ss);
        } else {
            setContent(new byte[0]);
        }
    }
    
    /**
     * Create a response to be send
     * 
     * @param version
     *            The http version for this response
     * @param status
     *            The status number of this response
     * @param message
     *            The status message
     * @param type
     *            The type of the content to be send
     * @param content
     *            The content to be send
     */
    public Response(String version, int status, String message, String type, byte[] content) {
        setHeader(new HTTPResponseHeader(message, status, version));
        setContent(content);
        getHeader().addHeaderField("Content-Length", Integer.toString(getContentLength()));
        getHeader().addHeaderField("Content-Type", type);
        getHeader().addHeaderField("Date", getServerTime());
    }
    
    private String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }
    
    /**
     * Create a response without content
     * 
     * @param version
     *            The http version
     * @param status
     *            The status code
     * @param message
     *            The status message
     */
    public Response(String version, int status, String message) {
        setHeader(new HTTPResponseHeader(message, status, version));
        setContent(new byte[0]);
    }
    
    private int getContentLength() {
        return getContent().length;
    }
    
    @Override
    public HTTPResponseHeader getHeader() {
        return (HTTPResponseHeader) super.getHeader();
    }
    
    protected void setHeader(HTTPResponseHeader header) {
        super.setHeader(header);
    }
    
    public URI getUri() {
        return uri;
    }
    
    private void setUri(URI uri) {
        this.uri = uri;
    }
    
    @Override
    public String toString() {
        StringBuilder messageBuilder = new StringBuilder();
        
        messageBuilder.append(getHeader());
        messageBuilder.append("\r\n");
        
        String type = getHeader().getHeaderField("Content-Type");
        
        // Only show the content if it's text
        if (type.contains("text")) {
            messageBuilder.append(getContentString());
        } else if (!type.equals("")) {
            // Just show the type if the content is something other than text.
            messageBuilder.append("Type: \"" + type + "\"");
        }
        
        return messageBuilder.toString();
    }
    
}
