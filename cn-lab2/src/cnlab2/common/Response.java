package cnlab2.common;

import java.io.IOException;

public class Response extends HTTPMessage {
    private URI uri;
    
    protected Response() {
    }
    
    public Response(SmartSocket ss, URI uri) throws IOException, SocketClosedException {
        setHeader(new HTTPResponseHeader(ss));
        setUri(uri);
        
        // Only read content if it was a successful response
        if (getHeader().getFirstLine().contains("200")) {
            readContent(ss);
        }
        else {
            setContent(new byte[0]);
        }
    }
    
    public Response(String version, int status, String message, String type, byte[] content) {
        setHeader(new HTTPResponseHeader(message, status, version));
        setContent(content);
        getHeader().addHeaderField("Content-Length", Integer.toString(getContentLength()));
        getHeader().addHeaderField("Content-Type", type);
    }
    
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
        
        if (type.contains("text")) {
            messageBuilder.append(getContentString());
        }
        else if (!type.equals("")) {
            messageBuilder.append("Type: \"" + type + "\"");
        }
        
        return messageBuilder.toString();
    }
    
}
