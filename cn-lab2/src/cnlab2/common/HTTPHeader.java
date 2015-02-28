package cnlab2.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class HTTPHeader {
	private String version;
	private final Map<String, String> headerMap = new LinkedHashMap<String, String>();

	public HTTPHeader(String version) {
		setVersion(version);
	}
	
	public HTTPHeader(BufferedReader in) throws IOException{
		parseFirstLine(in);
		parseMap(in);
	}
		
	protected abstract void parseFirstLine(BufferedReader in) throws IOException;
	
	private void parseMap(BufferedReader in) throws IOException{
		String line;
		boolean readyForKey = true;
		String key = null;
		StringBuilder valueBuilder = null;
		while (true) {
			line = in.readLine();
			
			if (line == null || line.equals("")) {
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
				this.addHeaderField(key, valueBuilder.toString());
				readyForKey = true;
			}
		}
		
	}
	
	public String getVersion() {
		return version;
	}

	protected void setVersion(String version) {
		this.version = version;
	}

	public Map<String, String> getHeaderMap() {
		return this.headerMap;
	}

	public void addHeaderField(String key, String value) {
		this.headerMap.put(key, value);
	}
}
