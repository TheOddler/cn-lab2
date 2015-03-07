package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.URI;

public abstract class Handler {
    
    private Client client;
    private URI uri;
    private SmartSocket smartSocket;
    
    public Handler(Client client, URI uri) throws UnknownHostException, IOException {
        setClient(client);
        setUri(uri);
        
        this.setSmartSocket(getClient().getSmartSocketFor(getUri()));
    }
    
    public abstract String getCommand();
    
    public void send() throws IOException {
        Request request = new Request(getCommand(), getUri(), client.getVersion(), "");
        request.getHeader().addHeaderField("Host", uri.getHost() + ":" + uri.getPort());
        sendRequest(getSmartSocket(), request);
    }
    
    protected void sendRequest(SmartSocket smartSocket, Request req) throws IOException {
        System.out.println("Request (" + getCommand() + "):\n" + req + "\nEnd req\n");
        smartSocket.send(req.toString());
    }
    
    public Response receive() throws IOException {
        return getResponse(getSmartSocket());
    }
    
    protected Response getResponse(SmartSocket smartSocket) throws IOException {
        Response resp = new Response(smartSocket);
        
        System.out.println("Response (" + getCommand() + "):\n" + resp + "\nEnd resp\n");
        
        return resp;
    }
    
    public Client getClient() {
        return client;
    }
    
    private void setClient(Client client) {
        this.client = client;
    }
    
    public URI getUri() {
        return uri;
    }
    
    private void setUri(URI uri) {
        this.uri = uri;
    }
    
    public SmartSocket getSmartSocket() {
        return smartSocket;
    }
    
    private void setSmartSocket(SmartSocket smartSocket) {
        this.smartSocket = smartSocket;
    }
    
}
