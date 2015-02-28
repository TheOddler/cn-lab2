package cnlab2.common;

import java.io.BufferedReader;
import java.io.IOException;

public class HTTPResponseHeader extends HTTPHeader{
	
	private int status;
	private String message;
	
	public HTTPResponseHeader(String message, int status, String version){
		super(version);
		setMessage(message);
		setStatus(status);
	}

	public HTTPResponseHeader(BufferedReader in) throws IOException {
		super(in);
	}

	@Override
	protected void parseFirstLine(BufferedReader in) throws IOException {
		String statusLine = in.readLine(); // lines[0]

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
	
	public String toString(){
	       StringBuilder headerBuilder = new StringBuilder();
	        headerBuilder.append(getVersion());
	        headerBuilder.append(" ");
	        headerBuilder.append(getStatus());
	        headerBuilder.append(" ");
	        headerBuilder.append(getMessage());
	        headerBuilder.append("\n");
	        for (String key : getHeaderMap().keySet()) {
	            headerBuilder.append(key);
	            headerBuilder.append(": ");
	            headerBuilder.append(getHeaderMap().get(key));
	            headerBuilder.append("\n");
	        }
	        return headerBuilder.toString();
	}
	

}
