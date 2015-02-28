package cnlab2.common;

import java.io.BufferedReader;
import java.io.IOException;

public class Request extends HTTPMessage {
    private HTTPRequestHeader header;
    
    public Request(BufferedReader in) throws IOException {
        setHeader(new HTTPRequestHeader(in));
        readContent(in);
    }
    
    public Request(String command, URI uri, String version, String content) {
        setHeader(new HTTPRequestHeader(command, uri, version));
        setContent(content);
    }
    
    @Override
    public String toString() {
        StringBuilder requestBuilder = new StringBuilder();
        
        requestBuilder.append(getHeader());
        
        if (!getContent().isEmpty()) {
            requestBuilder.append("\n"); // blank line between headers & content
            requestBuilder.append(getContent());
        }
        
        requestBuilder.append("\n");
        
        return requestBuilder.toString();
    }
    
    public HTTPRequestHeader getHeader() {
        return this.header;
    }
    
    private void setHeader(HTTPRequestHeader header) {
        this.header = header;
    }
    
}
