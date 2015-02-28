package cnlab2.common;

import cnlab2.client.URI;

public class Request {
	
	private String command;
	private URI uri;
	private String version;
	private String content;

	public Request(String command, URI uri, String version, String content){
		setCommand(command);
		setUri(uri);
		setVersion(version);
		setContent(content);
	}
	
	@Override
	public String toString() {
		StringBuilder requestBuilder = new StringBuilder();
		requestBuilder.append(command);
		requestBuilder.append(" ");
		requestBuilder.append(getUri().getResource());
		requestBuilder.append(" ");
		requestBuilder.append(getVersion());
		requestBuilder.append("\n\n");
		requestBuilder.append(content);
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
	
	
}
