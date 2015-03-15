package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;
import cnlab2.common.SocketClosedException;
import cnlab2.common.URI;

/**
 * The base class for all handlers
 *
 */
public abstract class Handler {
    
    private Client client;
    private URI uri;
    private SmartSocket smartSocket;
    
    /**
     * Create a handler for a client
     * @param client
     * @param uri
     * @throws UnknownHostException
     * @throws IOException
     */
    public Handler(Client client, URI uri) throws UnknownHostException, IOException {
        setClient(client);
        setUri(uri);
        
        // ask for a socket to the client
        this.setSmartSocket(getClient().getSmartSocketFor(getUri()));
    }
    
    /**
     * Get the command string for this handler
     * @return
     */
    public abstract String getCommand();
    
    /**
     * Send the request over the socket
     * @throws IOException
     */
    public void send() throws IOException {
        // create the request
        Request request = new Request(getCommand(), getUri(), client.getVersion(), "");
        // add the minimal host header, do this for both 1.0 and 1.1
        // This isn't mandatory for 1.0 but many servers still expect it so we just send it always.
        request.getHeader().addHeaderField("Host", uri.getHost() + ":" + uri.getPort());
        // Actually send the request
        sendRequest(getSmartSocket(), request);
    }
    
    protected void sendRequest(SmartSocket smartSocket, Request req) throws IOException {
        // Show the request to be send as asked in the assignment.
        System.out.println("\nSending request:");
        System.out.println(req.toString());
        smartSocket.send(req.toString());
        System.out.println("\nFinished sending request.");
    }
    
    /**
     * Receive the response
     * @return
     * @throws IOException
     * @throws SocketClosedException
     */
    public Response receive() throws IOException, SocketClosedException {
        return getResponse(getSmartSocket());
    }
    
    protected Response getResponse(SmartSocket smartSocket) throws IOException, SocketClosedException {
        // Show the received response as asked in the assignment.
        System.out.println("\nReceiving response:");
        Response resp = new Response(smartSocket, uri);
        System.out.println(resp.toString());
        System.out.println("\nFinished receiving response.");
        // return the response so we can use it's data
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
