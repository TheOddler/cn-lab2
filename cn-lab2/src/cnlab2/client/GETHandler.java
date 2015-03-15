package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cnlab2.common.Request;
import cnlab2.common.URI;

/**
 * The handler for GET requests
 *
 */
public class GETHandler extends Handler {
	
	public GETHandler(Client client, URI uri) throws UnknownHostException, IOException {
		super(client, uri);
	}
	
	/**
	 * The command for a GET request is GET
	 */
	@Override
	public String getCommand() {
		return "GET";
	}
	
	/**
	 * GET has a specialized send method with some extra info
	 */
	@Override
	public void send() throws IOException {
        Request request = new Request(getCommand(), getUri(), getClient().getVersion(), "");
        
        // Get requests should add the if-modified-since header if we already have a local
        // version of the file we're requesting so no redundant data is send back
        Date modifiedDate = getUri().getLocalLastModifiedDate();
        if (modifiedDate != null) {
            // Sat, 29 Oct 1994 19:43:31 GMT
            DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            request.getHeader().addHeaderField("If-Modified-Since", df.format(modifiedDate));
        }
        
        // Always add host header
        request.getHeader().addHeaderField("Host", getUri().getHost() + ":" + getUri().getPort());
        
        sendRequest(getSmartSocket(), request);
    }
	
}
