package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import cnlab2.common.Request;
import cnlab2.common.URI;

/**
 * The handler for POST handlers
 *
 */
public class POSTHandler extends Handler {
    
    public POSTHandler(Client client, URI uri) throws UnknownHostException, IOException {
        super(client, uri);
    }
    
    @Override
    public String getCommand() {
        return "POST";
    }
    
    /**
     * The specialized send method for a POST handler
     * Will ask for additional data to be send
     */
    @Override
    public void send() throws IOException {
        // create a base request
        Request request = new Request(getCommand(), getUri(), getClient().getVersion(), "");
        request.getHeader().addHeaderField("Host", getUri().getHost() + ":" + getUri().getPort());
        
        // ask for additional data to the user
        System.out.println(getCommand() + ", please enter content (end with 2 empty lines): ");
        
        Scanner sc = new Scanner(System.in); // Do not close in case other Post
                                             // handlers follow
        StringBuilder contentBuilder = new StringBuilder();
        String previous = "";
        String current = "";
        while (sc.hasNextLine()) {
            current = sc.nextLine();
            if (previous.equals("") && current.equals("")) {
                break;
            }
            contentBuilder.append(current);
            contentBuilder.append("\r\n");
            previous = current;
        }
        
        // strip the extra empty lines
        String content = contentBuilder.toString().trim();
        
        // Add a header noting the content length
        request.getHeader().addHeaderField("Content-Length", Integer.toString(content.length()));
        // Add the content
        request.setContent(content.getBytes());
        
        // send the request
        sendRequest(getSmartSocket(), request);
    }
    
}
