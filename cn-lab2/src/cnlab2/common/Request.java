package cnlab2.common;

import java.io.IOException;

public class Request extends HTTPMessage {
    
    public Request(SmartSocket ss) throws IOException, SocketClosedException {
        setHeader(new HTTPRequestHeader(ss));
        readContent(ss);
    }
    
    public Request(String command, URI uri, String version, String content) {
        setHeader(new HTTPRequestHeader(command, uri, version));
        setContent(content.getBytes());
    }
    
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
