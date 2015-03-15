package cnlab2.client;

import java.io.IOException;

import cnlab2.common.HTTPResponseHeader;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.SocketClosedException;

/**
 * A response that doesn't read content even when the headers specify a content-length
 * Used for head/options handlers
 *
 */
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
