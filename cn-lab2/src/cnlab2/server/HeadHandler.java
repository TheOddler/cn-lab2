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
        Response result = new Response(r.getHeader().getVersion(), r.getHeader().getStatus(), r.getHeader().getMessage(), "");
        for (String s : r.getHeader().getHeaderMap().keySet()) {
            result.getHeader().addHeaderField(s, r.getHeader().getHeaderMap().get(s));
        }
        return result;
    }
    
}
