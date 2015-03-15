package cnlab2.server;

import java.io.IOException;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class NotImplementedHandler extends Handler {

    /**
     * A simple handler for requests that are not implemented.
     * @param socket
     * @param request
     */
    public NotImplementedHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }

    @Override
    public Response handle() throws IOException {
        // Simple return a 501 Not Implemented response.
        return new Response(
                getRequest().getHeader().getVersion(),
                501,
                "Not Implemented");
    }
    
}
