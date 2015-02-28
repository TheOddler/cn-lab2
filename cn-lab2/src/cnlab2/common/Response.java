package cnlab2.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
	private String version;
	private int status;
	private String message;
	private final Map<String, String> headerMap = new LinkedHashMap<String, String>();
	private String content;

	private Response() {
		
	}
	
	public Response(String response) {
		parseResponse(response);
	}
	
	public Response(String version, int status, String message, String content){
		setVersion(version);
		setStatus(status);
		setMessage(message);
		setContent(content);
	}
	
	public static Response ReadFirstResponse(BufferedReader inFromServer) throws IOException {
		Response resp = new Response();
		
		System.out.println("Start reading...");
		
		String statusLine = inFromServer.readLine(); // lines[0]
		
		if (statusLine == null) {
		    System.out.println("No more lines: " + inFromServer);
		}

		int firstSpace = statusLine.indexOf(" ");
		String version = statusLine.substring(0, firstSpace);
		int secondSpace = statusLine.indexOf(" ", firstSpace + " ".length());
		String statusCodeStr = statusLine.substring(firstSpace + " ".length(),
				secondSpace);
		String statusMessageStr = statusLine.substring(secondSpace
				+ " ".length());

		resp.setVersion(version);
		resp.setStatus(Integer.parseInt(statusCodeStr));
		resp.setMessage(statusMessageStr);

		String line;
		boolean readyForKey = true;
		//int currentLineIndex = 1;
		String key = null;
		StringBuilder valueBuilder = null;
		while (true /*currentLineIndex < lines.length*/) {
			line = inFromServer.readLine(); // lines[currentLineIndex];
			
			if (line == null || line.equals("")) {
				//currentLineIndex++;
				break;
			}
			if (readyForKey) {
				int collumnIndex = line.indexOf(":");
				key = line.substring(0, collumnIndex);
				line = line.substring(collumnIndex + ":".length());
				readyForKey = false;

				valueBuilder = new StringBuilder();
			}
			valueBuilder.append(line.trim());
			if (!line.endsWith(",")) {
				resp.addHeaderField(key, valueBuilder.toString());
				readyForKey = true;
			}
			//currentLineIndex++;
		}

		StringBuilder contentBuilder = new StringBuilder();
		/*for (; currentLineIndex < lines.length; currentLineIndex++) {
			contentBuilder.append(lines[currentLineIndex]);
			contentBuilder.append("\n");
		}*/
		int totalRead = 0;
		if (resp.getHeaderMap().containsKey("Content-Length")) {
			int length = Integer.parseInt(resp.getHeaderMap().get("Content-Length"));
			while (totalRead < length) {
				line = inFromServer.readLine();
				if (line == null) break;
				
				contentBuilder.append(line);
				contentBuilder.append("\n");
				
				totalRead += line.length();
				totalRead += 1; //the newline character
			}
		}
		System.out.println("totalRead: " + totalRead);
		
		if (contentBuilder.toString().equals(""))
			contentBuilder.append("\n");
		resp.setContent(contentBuilder.toString());
		
		
		
		
		return resp;
	}

	private void parseResponse(String response) {
		String lines[] = response.split("\\r?\\n");
		String statusLine = lines[0];

		int firstSpace = statusLine.indexOf(" ");
		String version = statusLine.substring(0, firstSpace);
		int secondSpace = statusLine.indexOf(" ", firstSpace + " ".length());
		String statusCodeStr = statusLine.substring(firstSpace + " ".length(),
				secondSpace);
		String statusMessageStr = statusLine.substring(secondSpace
				+ " ".length());

		setVersion(version);
		setStatus(Integer.parseInt(statusCodeStr));
		setMessage(statusMessageStr);

		String line;
		boolean readyForKey = true;
		int currentLineIndex = 1;
		String key = null;
		StringBuilder valueBuilder = null;
		while (currentLineIndex < lines.length) {
			line = lines[currentLineIndex];
			if (line.equals("")) {
				currentLineIndex++;
				break;
			}
			if (readyForKey) {
				int collumnIndex = line.indexOf(":");
				key = line.substring(0, collumnIndex);
				line = line.substring(collumnIndex + ":".length());
				readyForKey = false;

				valueBuilder = new StringBuilder();
			}
			valueBuilder.append(line.trim());
			if (!line.endsWith(",")) {
				addHeaderField(key, valueBuilder.toString());
				readyForKey = true;
			}
			currentLineIndex++;
		}

		StringBuilder contentBuilder = new StringBuilder();
		for (; currentLineIndex < lines.length; currentLineIndex++) {
			contentBuilder.append(lines[currentLineIndex]);
			contentBuilder.append("\n");
		}
		if (contentBuilder.toString().equals(""))
			contentBuilder.append("\n");
		setContent(contentBuilder.toString());

	}

	public String getHeader() {
		StringBuilder headerBuilder = new StringBuilder();
		headerBuilder.append(getVersion());
		headerBuilder.append(" ");
		headerBuilder.append(getStatus());
		headerBuilder.append(" ");
		headerBuilder.append(getMessage());
		headerBuilder.append("\n");
		for (String key : headerMap.keySet()) {
			headerBuilder.append(key);
			headerBuilder.append(": ");
			headerBuilder.append(headerMap.get(key));
			headerBuilder.append("\n");
		}
		return headerBuilder.toString();

	}

	public String toString() {
		return getHeader() + "\n" + getContent() + "\n";
	}

	private void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getVersion() {
		return version;
	}

	private void setVersion(String version) {
		this.version = version;
	}

	public int getStatus() {
		return status;
	}

	private void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getHeaderMap() {
		return this.headerMap;
	}

	public void addHeaderField(String key, String value) {
		this.headerMap.put(key, value);
	}
}
