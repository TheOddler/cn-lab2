package cnlab2.common;

import java.io.IOException;

public class ContentlessResponse extends Response {
    
    public ContentlessResponse(SmartSocket ss) throws IOException, SocketClosedException {
        setHeader(new HTTPResponseHeader(ss));
        setContent(new byte[0]);
    }
    
    @Override
    public String toString() {
        StringBuilder messageBuilder = new StringBuilder();
        
        messageBuilder.append(getHeader());
        messageBuilder.append("\r\n");
        
        return messageBuilder.toString();
    }
    
}
