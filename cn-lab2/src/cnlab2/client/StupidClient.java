package cnlab2.client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cnlab2.common.Response;
import cnlab2.common.URI;

public abstract class StupidClient {

	public static void main(String[] args) throws Exception {

		if (args.length != 4) {
			System.out.println("Usage: Command Uri Port Version");
		}
		
		String command = args[INDEX_COMMAND];
		int port = Integer.parseInt(args[INDEX_PORT]);
		URI uri = new URI(args[INDEX_URI], port);
		
		String version = args[INDEX_VERSION];
		
		Client client = null;
		if (version.equals("1.0")) {
			client = new HTTP10Client();
		} else if (version.equals("1.1")) {
			client = new HTTP11Client();
		} else {
			throw new IllegalAccessException("Invalid version: " + version);
		}
		
		List<Response> responses = client.handle(new HTTPCommand(command, uri));
		
		
		// Print images sources
		System.out.println();
		System.out.println("IMAGES:");
		
		List<String> resoruces = new ArrayList<>();
		Response response = responses.get(0);
		if (response.getHeader().getHeaderField("Content-Type").contains("text/html")) {
		    String imgRegex = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
		    Pattern p = Pattern.compile(imgRegex);
		    Matcher m = p.matcher(response.getContent());
		    while (m.find()) {
		        resoruces.add(m.group(1));
		    }
		}
		
        System.out.println(resoruces);
	}
	
	private static final int INDEX_COMMAND = 0;
	private static final int INDEX_URI= 1;
	private static final int INDEX_PORT = 2;
	private static final int INDEX_VERSION= 3;
}
