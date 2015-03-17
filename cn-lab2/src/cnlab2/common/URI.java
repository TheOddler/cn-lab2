package cnlab2.common;

import java.io.File;
import java.util.Date;

/**
 * A simple URI class to parse and manage uri
 *
 */
public class URI {
    private String protocol;
    private String host;
    private String resource;
    private int port;
    
    /**
     * Contsructor for creating a URI without explicit port
     * 
     * @param protocol
     * @param host
     * @param resource
     */
    public URI(String protocol, String host, String resource) {
        setProtocol(protocol);
        setHost(host);
        setResource(resource);
    }
    
    /**
     * Constructor for created a URI using the default protocol.
     * 
     * @param host
     * @param resource
     */
    public URI(String host, String resource) {
        this(DEFAULT_PROTOCOL, host, resource);
    }
    
    /**
     * Constructor to create a URI with the given port and default protocol.
     * 
     * @param host
     * @param resource
     * @param port
     */
    public URI(String host, String resource, int port) {
        this(host, resource);
        setPort(port);
    }
    
    public URI(String str) throws IllegalAccessException {
        this(str, DEFAULT_PORT);
    }
    
    /**
     * Constructor to create a uri based on a full string
     * 
     * @param uriStr
     *            The uri string Allowed formats:
     *            http://example.com:80/some/path http://example.com/some/path
     *            http://example.com example.com example.com:80/some/path
     *            example.com/some/path
     * @param port
     * @throws IllegalAccessException
     */
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
        
        // Check for host and resource
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
    
    /**
     * Get the date of the local version of the file this URI points to
     * 
     * @return The date Returns null if no file exists locally
     */
    public Date getLocalLastModifiedDate() {
        File f = new File(getLocalLocation());
        if (!f.exists()) { return null; }
        
        Long lastModified = f.lastModified();
        Date lastModifiedDate = new Date(lastModified);
        return lastModifiedDate;
    }
    
    /**
     * Get the path of the local file this URI points to.
     * 
     * @return
     */
    public String getLocalLocation() {
        String host = getHost();
        String resource = getResource();
        if (resource.equals("/")) resource = "/index.html";
        return LocalRootPath + host + resource;
    }
    
    private static final String LocalRootPath = System.getProperty("user.dir") + "/server-root/";
    
    private static final String DEFAULT_RESOURCE = "/index.html";
    private static final String DEFAULT_PROTOCOL = "http";
    private static final int DEFAULT_PORT = 80;
}
