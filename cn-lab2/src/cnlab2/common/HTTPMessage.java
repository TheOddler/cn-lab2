package cnlab2.common;

import java.io.IOException;

/**
 * An abstract version of a http message (request or response)
 *
 */
public abstract class HTTPMessage {
    private byte[] content;
    private HTTPHeader header;
    
    /**
     * Read the content from a socket based on the Content-Length header 
     * @param ss
     * @throws IOException
     */
    protected void readContent(SmartSocket ss) throws IOException {
        if (getHeader().getHeaderMap().containsKey("Content-Length")) {
            int length = Integer.parseInt(getHeader().getHeaderMap().get("Content-Length"));
            byte[] content = ss.getBytes(length);
            setContent(content);
        } else {
            // if no content-length header is present, we assume no content is present.
            setContent(new byte[0]);
        }
    }
    
    public byte[] getContent() {
        return content;
    }
    
    public String getContentString() {
        return new String(getContent());
    }
    
    public void setContent(byte[] content) {
        this.content = content;
    }
    
    public HTTPHeader getHeader() {
        return header;
    }
    
    public void setHeader(HTTPHeader header) {
        this.header = header;
    }
    
}
