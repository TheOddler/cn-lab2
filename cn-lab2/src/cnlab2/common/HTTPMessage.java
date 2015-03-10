package cnlab2.common;

import java.io.IOException;

public abstract class HTTPMessage {
    private byte[] content;
    private HTTPHeader header;
    
    protected void readContent(SmartSocket ss) throws IOException {
        if (getHeader().getHeaderMap().containsKey("Content-Length")) {
            int length = Integer.parseInt(getHeader().getHeaderMap().get("Content-Length"));
            byte[] content = ss.getBytes(length);
            setContent(content);
        } else {
            throw new IllegalArgumentException("No content length field");
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
