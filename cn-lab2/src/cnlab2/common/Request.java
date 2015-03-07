package cnlab2.common;

import java.io.IOException;

public class Request extends HTTPMessage {
    private HTTPRequestHeader header;
    
    public Request(SmartSocket ss) throws IOException {
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
    public String toString() {
        StringBuilder requestBuilder = new StringBuilder();
        
        requestBuilder.append(getHeader());
        requestBuilder.append(new String(getContent()));
        requestBuilder.append("\r\n\r\n");
        return requestBuilder.toString();
    }
    
    public HTTPRequestHeader getHeader() {
        return this.header;
    }
    
    private void setHeader(HTTPRequestHeader header) {
        this.header = header;
    }
    
}
