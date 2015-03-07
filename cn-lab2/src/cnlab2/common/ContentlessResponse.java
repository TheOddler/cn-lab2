package cnlab2.common;

import java.io.IOException;

public class ContentlessResponse extends Response {
    
    public ContentlessResponse(SmartSocket ss) throws IOException {
        setHeader(new HTTPResponseHeader(ss));
        setContent(null);
    }
    
}
