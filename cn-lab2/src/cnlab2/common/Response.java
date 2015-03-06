package cnlab2.common;

import java.io.IOException;

public class Response extends HTTPMessage {
    private HTTPResponseHeader header;
    
    public Response(SmartSocket in) throws IOException {
        setHeader(new HTTPResponseHeader(in));
        readContent(in);
    }
    
    public Response(String version, int status, String message, String content) {
        setHeader(new HTTPResponseHeader(message, status, version));
        setContent(content);
        getHeader().addHeaderField("Content-Length", Integer.toString(getContentLength()));
        getHeader().addHeaderField("Content-Type", "text/html");
    }
    
    private int getContentLength() {
        return getContent().length();
    }
    
    public String toString() {
        return getHeader().toString() + "\n" + getContent() + "\n";
    }
    
    public HTTPResponseHeader getHeader() {
        return this.header;
    }
    
    private void setHeader(HTTPResponseHeader header) {
        this.header = header;
    }
    
}
