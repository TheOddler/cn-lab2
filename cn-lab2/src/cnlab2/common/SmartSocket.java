package cnlab2.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A wrapper for a socket
 * Helps with re-using a socket connecting and read/writing to it without losing data.
 */
public class SmartSocket {
    private URI creationUri;
    
    private Socket socket;
    private BufferedInputStream in;
    private DataOutputStream out;
    
    public SmartSocket(Socket socket) throws IOException {
        setSocket(socket);
        setIn(new BufferedInputStream(socket.getInputStream()));
        setOut(new DataOutputStream(socket.getOutputStream()));
    }
    
    // only host and port is used of the URI, resource is ignored.
    public SmartSocket(URI uri) throws IOException {
        this.creationUri = uri;
        
        RefreshIfNeeded();
    }
    
    /**
     * Re-open the socket if it has been closed
     * @throws UnknownHostException
     * @throws IOException
     */
    public void RefreshIfNeeded() throws UnknownHostException, IOException {
        if (socket == null || socket.isClosed()) {
            this.setSocket(new Socket(this.creationUri.getHost(), this.creationUri.getPort()));
            
            setIn(new BufferedInputStream(getSocket().getInputStream()));
            
            this.setOut(new DataOutputStream(this.getSocket().getOutputStream()));
        }
    }
    
    /**
     * Check if this socket can be used for requests to the given URI
     * @param uri URI
     * @return if it can be used
     */
    public boolean canBeUsedFor(URI uri) {
        boolean equalHosts = creationUri.getHost().equals(uri.getHost());
        boolean equalPorts = creationUri.getPort() == uri.getPort();
        return equalHosts && equalPorts;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    private void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    /**
     * Read n bytes from this socket
     * Blocks if no data is present
     * @param n
     * @return
     * @throws IOException
     */
    public byte[] getBytes(int n) throws IOException {
        byte[] bytes = new byte[n];
        
        // buildin function to read n bytes doesn't work propertly, gives half-data a lot
        /*int realn = in.read(bytes);
        if (n != realn) {
            System.out.println("TOO SHORT CONTENT OOH NOOOEEESSS!!!!! " + realn);
        }*/
        
        // read n bytes and hold them
        for (int i = 0; i < n; ++i) {
            bytes[i] = (byte)in.read();
        }
        
        return bytes;
    }
    
    /**
     * Read n bytes as a string.
     * @param n n
     * @return the read string
     * @throws IOException
     */
    public String getString(int n) throws IOException {
        return new String(getBytes(n));
    }
    
    /**
     * Read a single line
     * Reads until \r\n or \n
     * @return the read line.
     * @throws IOException
     * @throws SocketClosedException
     */
    public String readLine() throws IOException, SocketClosedException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int c = 0;
        
        while (true) {
            c = in.read();
            
            if (c == -1) {
                break;
            }
            if (c == '\r') {
                in.mark(2);
                c = in.read();
                if (c == '\n') {
                    break;
                }
            }
            if (c == '\n') {
                break;
            }
            byteArrayOutputStream.write(c);
        }
        
        if (c == -1 && byteArrayOutputStream.size() == 0) {
            throw new SocketClosedException("Socket has been closed");
        }
        String line = byteArrayOutputStream.toString("UTF-8");
        return line;
    }
    
    /**
     * Send bytes through this socket
     * @param bytes
     * @throws IOException
     */
    public void send(byte[] bytes) throws IOException {
        out.write(bytes);
    }
    
    /**
     * Send a string through this socket
     * @param str
     * @throws IOException
     */
    public void send(String str) throws IOException {
        out.writeBytes(str);
    }
    
    /**
     * Send a response through this socket
     * @param resp
     * @throws IOException
     */
    public void send(Response resp) throws IOException {
        out.writeBytes(resp.getHeader().toString() + "\r\n");
        out.write(resp.getContent());
    }
    
    private void setIn(BufferedInputStream in) {
        this.in = in;
    }
    
    private void setOut(DataOutputStream out) {
        this.out = out;
    }
    
    @Override
    public String toString() {
        return "Socket [host=" + creationUri.getHost() + ", port=" + creationUri.getPort() + "]";
    }
}
