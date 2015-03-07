package cnlab2.common;

import java.io.IOException;

public class Response extends HTTPMessage {
    private URI uri;
    
    protected Response() {
    }
    
    public Response(SmartSocket ss, URI uri) throws IOException {
        setHeader(new HTTPResponseHeader(ss));
        setUri(uri);
        
        // UGLY! FIXME
        if (getHeader().getFirstLine().contains("200")) {
            readContent(ss);
        }
        
        System.out.println(getHeader());
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
    
}
