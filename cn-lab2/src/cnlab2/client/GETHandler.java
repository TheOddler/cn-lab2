package cnlab2.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cnlab2.common.Request;
import cnlab2.common.URI;

public class GETHandler extends Handler {
	
	public GETHandler(Client client, URI uri) throws UnknownHostException, IOException {
		super(client, uri);
	}
	
	@Override
	public String getCommand() {
		return "GET";
	}
	
	@Override
	public void send() throws IOException {
        Request request = new Request(getCommand(), getUri(), getClient().getVersion(), "");
        
        Date modifiedDate = getUri().getLocalLastModifiedDate();
        if (modifiedDate != null) {
            // Sat, 29 Oct 1994 19:43:31 GMT
            DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            request.getHeader().addHeaderField("If-Modified-Since", df.format(modifiedDate));
        }
        
        request.getHeader().addHeaderField("Host", getUri().getHost() + ":" + getUri().getPort());
        
        sendRequest(getSmartSocket(), request);
    }
	
}
