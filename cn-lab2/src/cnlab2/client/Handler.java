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
        System.out.println("\nSending request:");
        System.out.println(req.toString());
        smartSocket.send(req.toString());
        System.out.println("\nFinished sending request.");
    }
    
    public Response receive() throws IOException {
        return getResponse(getSmartSocket());
    }
    
    protected Response getResponse(SmartSocket smartSocket) throws IOException {
        System.out.println("\nReceiving response:");
        Response resp = new Response(smartSocket, uri);
        System.out.println(resp.toString());
        System.out.println("\nFinished receiving response.");
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
