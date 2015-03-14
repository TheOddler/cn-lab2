package cnlab2.server;

import java.io.IOException;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class NotImplementedHandler extends Handler {

    public NotImplementedHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }

    @Override
    public Response handle() throws IOException {
        return new Response(
                getRequest().getHeader().getVersion(),
                501,
                "Not Implemented");
    }
    
}
