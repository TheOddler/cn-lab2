package cnlab2.common;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The abstract version of a http header
 *
 */
public abstract class HTTPHeader {
    private String version;
    private final Map<String, String> headerMap = new LinkedHashMap<String, String>();
    
    public HTTPHeader(String version) {
        setVersion(version);
    }
    
    /**
     * Create a http header by readin from a socket
     * @param ss
     * @throws IOException
     * @throws SocketClosedException
     */
    public HTTPHeader(SmartSocket ss) throws IOException, SocketClosedException {
        parseFirstLine(ss);
        parseMap(ss);
    }
    
    protected abstract void parseFirstLine(SmartSocket ss) throws IOException, SocketClosedException;
    
    protected abstract String getFirstLine();
    
    /**
     * Read all the headers from a socket
     * @param ss
     * @throws IOException
     * @throws SocketClosedException
     */
    private void parseMap(SmartSocket ss) throws IOException, SocketClosedException {
        String line;
        boolean readyForKey = true;
        String key = null;
        StringBuilder valueBuilder = null;
        while (true) {
            line = ss.readLine();
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
    
    @Override
    public String toString() {
        StringBuilder headerBuilder = new StringBuilder();
        
        headerBuilder.append(getFirstLine());
        for (String key : getHeaderMap().keySet()) {
            headerBuilder.append(key);
            headerBuilder.append(": ");
            headerBuilder.append(getHeaderMap().get(key));
            headerBuilder.append("\r\n");
        }
        return headerBuilder.toString();
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
    
    public String getHeaderField(String key) {
        if (headerMap.containsKey(key)) {
            return headerMap.get(key);
        } else {
            return "";
        }
    }
}
