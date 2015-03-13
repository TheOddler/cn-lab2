package cnlab2.server;

import java.io.IOException;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class HeadHandler extends Handler {
    
    public HeadHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }
    
    @Override
    public Response handle() throws IOException {
        GetHandler h = new GetHandler(getSocket(), getRequest());
        Response r = h.handle();
        r.setContent(new byte[0]);
        return r;
    }
    
}
