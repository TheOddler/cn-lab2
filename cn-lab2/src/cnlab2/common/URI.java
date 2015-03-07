package cnlab2.common;

public class URI {
    private String protocol;
    private String host;
    private String resource;
    private int port;
    
    public URI(String protocol, String host, String resource) {
        setProtocol(protocol);
        setHost(host);
        setResource(resource);
    }
    
    public URI(String host, String resource) {
        this(DEFAULT_PROTOCOL, host, resource);
    }
    
    public URI(String uriStr, int port) throws IllegalAccessException {
        if (uriStr == null) { throw new IllegalAccessException("null URI gotten"); }
        
        // This parser should work in the following cases:
        // http://example.com:80/some/path
        // http://example.com/some/path
        // http://example.com
        // example.com
        // example.com:80/some/path
        // example.com/some/path
        
        // Check for protocol
        if (uriStr.contains("://")) {
            int index = uriStr.indexOf("://");
            setProtocol(uriStr.substring(0, index));
            uriStr = uriStr.substring(index + "://".length());
        }
        
        // Check for host
        if (uriStr.contains(":")) {
            throw new IllegalArgumentException("No port in URI's plz");
        } else if (uriStr.contains("/")) {
            int index = uriStr.indexOf("/");
            setHost(uriStr.substring(0, index));
            setResource(uriStr.substring(index));
        } else {
            setHost(uriStr);
            setResource("/");
        }
        
        this.setPort(port);
    }
    
    @Override
    public String toString() {
        return "URI " + "[" + "protocol=" + getProtocol() + ", " + "host=" + getHost() + ", " + "resource=" + getResource() + "]";
    }
    
    public String getResource() {
        return resource;
    }
    
    private void setResource(String resource) {
        if (resource.isEmpty()) {
            this.resource = DEFAULT_RESOURCE;
        } else {
            this.resource = resource;
        }
    }
    
    public String getHost() {
        return host;
    }
    
    private void setHost(String host) {
        this.host = host;
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    private void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    public int getPort() {
        return port;
    }
    
    private void setPort(int port) {
        this.port = port;
    }
    
    private static final String DEFAULT_RESOURCE = "/index.html";
    private static final String DEFAULT_PROTOCOL = "http";
}
