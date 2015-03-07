package cnlab2.common;

import java.io.IOException;

public class Response extends HTTPMessage {
    //private HTTPResponseHeader header;
    
    protected Response() {
    }
    
    public Response(SmartSocket ss) throws IOException {
        System.out.println("reading header");
        setHeader(new HTTPResponseHeader(ss));
        readContent(ss);
    }
    
    public Response(String version, int status, String message, String content) {
        setHeader(new HTTPResponseHeader(message, status, version));
        setContent(content.getBytes());
        getHeader().addHeaderField("Content-Length", Integer.toString(getContentLength()));
        getHeader().addHeaderField("Content-Type", "text/html");
    }
    
    private int getContentLength() {
        return getContent().length;
    }
    
    public String toString() {
        return getHeader().toString() + new String(getContent());
    }
    
    @Override
    public HTTPResponseHeader getHeader() {
        return (HTTPResponseHeader) super.getHeader();
    }
    
    protected void setHeader(HTTPResponseHeader header) {
        super.setHeader(header);
    }
    
}
