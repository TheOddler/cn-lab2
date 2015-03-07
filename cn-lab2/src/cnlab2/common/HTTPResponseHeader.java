package cnlab2.common;

import java.io.IOException;

public class HTTPResponseHeader extends HTTPHeader {
    
    private int status;
    private String message;
    
    public HTTPResponseHeader(String message, int status, String version) {
        super(version);
        setMessage(message);
        setStatus(status);
    }
    
    public HTTPResponseHeader(SmartSocket ss) throws IOException {
        super(ss);
    }
    
    @Override
    protected void parseFirstLine(SmartSocket ss) throws IOException {
        String statusLine = ss.readLine(); // lines[0]
        
        int firstSpace = statusLine.indexOf(" ");
        String version = statusLine.substring(0, firstSpace);
        int secondSpace = statusLine.indexOf(" ", firstSpace + " ".length());
        String statusCodeStr = statusLine.substring(firstSpace + " ".length(), secondSpace);
        String statusMessageStr = statusLine.substring(secondSpace + " ".length());
        
        setVersion(version);
        setStatus(Integer.parseInt(statusCodeStr));
        setMessage(statusMessageStr);
    }
    
    @Override
    protected String getFirstLine() {
        return getVersion() + " " + getStatus() + " " + getMessage() + "\r\n";
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
    
}
