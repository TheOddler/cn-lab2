package cnlab2.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import cnlab2.client.URI;

public class Request {
	
	private String command;
	private URI uri;
	private String version;
	private final Map<String, String> headerMap = new LinkedHashMap<String, String>();
	private String content;

	public Request(String command, URI uri, String version, String content){
		setCommand(command);
		setUri(uri);
		setVersion(version);
		setContent(content);
	}
	
	public static Request buildRequest(BufferedReader reader) throws IOException{
		String statusLine = reader.readLine();

		int firstSpace = statusLine.indexOf(" ");
		String command = statusLine.substring(0, firstSpace);
		int secondSpace = statusLine.indexOf(" ", firstSpace + " ".length());
		String resourceStr = statusLine.substring(firstSpace + " ".length(),
				secondSpace);
		String versionStr = statusLine.substring(secondSpace
				+ " ".length());
		
		
		//FIXME
		URI uri = null;
		String content = null;
		
		Request r = new Request(command, uri, versionStr, content);
		return r;
	}
	
	@Override
	public String toString() {
		StringBuilder requestBuilder = new StringBuilder();
		requestBuilder.append(command);
		requestBuilder.append(" ");
		requestBuilder.append(getUri().getResource());
		requestBuilder.append(" ");
		requestBuilder.append(getVersion());
		requestBuilder.append("\n");
		requestBuilder.append("Host: ");
		requestBuilder.append(uri.getHost());
		requestBuilder.append(":");
		requestBuilder.append(uri.getPort());
		
		if(!content.isEmpty()) {
			requestBuilder.append("\n\n"); //blank line between headers & content
			requestBuilder.append(content);
		}
		
		requestBuilder.append("\n\n");
		
		return requestBuilder.toString();
	}

	public String getCommand() {
		return command;
	}

	private void setCommand(String command) {
		this.command = command;
	}

	public URI getUri() {
		return uri;
	}

	private void setUri(URI uri) {
		this.uri = uri;
	}

	public String getVersion() {
		return version;
	}

	private void setVersion(String version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	private void setContent(String content) {
		this.content = content;
	}

	public Map<String, String> getHeaderMap() {
		return this.headerMap;
	}

	public void addHeaderField(String key, String value) {
		this.headerMap.put(key, value);
	}
	
}
