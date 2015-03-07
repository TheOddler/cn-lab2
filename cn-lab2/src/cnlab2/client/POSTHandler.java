package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import cnlab2.common.Request;
import cnlab2.common.URI;

public class POSTHandler extends Handler {
    
    public POSTHandler(Client client, URI uri) throws UnknownHostException, IOException {
        super(client, uri);
    }
    
    @Override
    public String getCommand() {
        return "POST";
    }
    
    @Override
    public void send() throws IOException {
        
        Request request = new Request(getCommand(), getUri(), getClient().getVersion(), "");
        request.getHeader().addHeaderField("Host", getUri().getHost() + ":" + getUri().getPort());
        
        System.out.println(getCommand() + ", please enter content: ");
        
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
            contentBuilder.append("\n");
            previous = current;
        }
        
        String content = contentBuilder.toString().trim();
        
        request.getHeader().addHeaderField("Content-Length", Integer.toString(content.length()));
        request.setContent(content.getBytes());
        
        sendRequest(getSmartSocket(), request);
    }
    
}
