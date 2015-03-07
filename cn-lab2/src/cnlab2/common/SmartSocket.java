package cnlab2.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SmartSocket {
    private URI creationUri;
    
    private Socket socket;
    private InputStream in;
    private DataOutputStream out;
    
    public SmartSocket(Socket socket) throws IOException {
        setSocket(socket);
        setIn(socket.getInputStream());
        setOut(new DataOutputStream(socket.getOutputStream()));
    }
    
    // only host and port is used of the URI, resource is ignored.
    public SmartSocket(URI uri) throws IOException {
        this.creationUri = uri;
        
        RefreshIfNeeded();
    }
    
    public void RefreshIfNeeded() throws UnknownHostException, IOException {
        if (socket == null || socket.isClosed()) {
            this.setSocket(new Socket(this.creationUri.getHost(), this.creationUri.getPort()));
            
            setIn(this.getSocket().getInputStream());
            
            this.setOut(new DataOutputStream(this.getSocket().getOutputStream()));
        }
    }
    
    public boolean canBeUsedFor(URI uri) {
        return creationUri.getHost() == uri.getHost() && creationUri.getPort() == uri.getPort();
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    private void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public byte[] getBytes(int n) throws IOException {
        byte[] bytes = new byte[n];
        in.read(bytes);
        return bytes;
    }
    
    public String getString(int n) throws IOException {
        return new String(getBytes(n));
    }
    
    public String readLine() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int c;
        
        for (c = in.read(); c != '\n' && c != -1; c = in.read()) {
            byteArrayOutputStream.write(c);
        }
        in.mark(2);
        int cp = in.read();
        if (cp != '\r') in.reset();
        
        if (c == -1 && byteArrayOutputStream.size() == 0) { return null; }
        String line = byteArrayOutputStream.toString("UTF-8");
        return line;
    }
    
    private void setIn(InputStream in) {
        this.in = in;
    }
    
    public DataOutputStream getOut() {
        return out;
    }
    
    private void setOut(DataOutputStream out) {
        this.out = out;
    }
}
