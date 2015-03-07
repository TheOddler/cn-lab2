package cnlab2.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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
    
    public void RefreshIfNeeded() throws UnknownHostException, IOException {
        if (socket == null || socket.isClosed()) {
            this.setSocket(new Socket(this.creationUri.getHost(), this.creationUri.getPort()));
            
            setIn(new BufferedInputStream(getSocket().getInputStream()));
            
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
        
        for (int i = 0; i < n; ++i) {
            bytes[i] = (byte)in.read();
        }
        
        /*int realn = in.read(bytes);
        if (n != realn) {
            System.out.println("TOO SHORT CONTENT OOH NOOOEEESSS!!!!! " + realn);
        }*/
        
        return bytes;
    }
    
    public String getString(int n) throws IOException {
        return new String(getBytes(n));
    }
    
    public String readLine() throws IOException {
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
        
        if (c == -1 && byteArrayOutputStream.size() == 0) { return null; }
        String line = byteArrayOutputStream.toString("UTF-8");
        return line;
    }
    
    public void send(byte[] bytes) throws IOException {
        out.write(bytes);
    }
    
    public void send(String str) throws IOException {
        out.writeBytes(str);
    }
    
    private void setIn(BufferedInputStream in) {
        this.in = in;
    }
    
    private void setOut(DataOutputStream out) {
        this.out = out;
    }
}
