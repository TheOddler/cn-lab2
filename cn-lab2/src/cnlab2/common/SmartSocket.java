package cnlab2.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class SmartSocket {
    private URI creationUri;
    
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    
    // only host and port is used of the URI, resource is ignored.
    public SmartSocket(URI uri) throws IOException {
        this.creationUri = uri;
        
        RefreshIfNeeded();
    }
    
    public void RefreshIfNeeded() throws UnknownHostException, IOException {
        if (socket == null || socket.isClosed()) {
            this.setSocket( new Socket(this.creationUri.getHost(), this.creationUri.getPort()) );
            
            setIn(new BufferedReader(
                    new InputStreamReader(this.getSocket().getInputStream()) ));
            
            this.setOut( new DataOutputStream(this.getSocket().getOutputStream()) );
        }
    }
    
    public boolean canBeUsedFor(URI uri) {
        return creationUri.getHost() == uri.getHost()
                && creationUri.getPort() == uri.getPort();
    }

    public Socket getSocket() {
        return socket;
    }

    private void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    private void setIn(BufferedReader in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    private void setOut(DataOutputStream out) {
        this.out = out;
    }
}
