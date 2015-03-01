package cnlab2.server;

import java.io.IOException;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public abstract class Handler {
    private SmartSocket socket;
    private Request request;
    
    public Handler(SmartSocket socket, Request request) {
        setSocket(socket);
        setRequest(request);
    }
    
    public abstract Response handle() throws IOException;
    
    public SmartSocket getSocket() {
        return socket;
    }
    
    private void setSocket(SmartSocket socket) {
        this.socket = socket;
    }
    
    public Request getRequest() {
        return request;
    }
    
    private void setRequest(Request request) {
        this.request = request;
    }
}
