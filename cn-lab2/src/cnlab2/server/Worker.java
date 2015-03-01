package cnlab2.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import cnlab2.common.Request;

public class Worker implements Runnable {
    
    private Socket socket;
    
    public Worker(Socket socket) {
        setSocket(socket);
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    private void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(getSocket().getOutputStream());
            
            Request r = new Request(inFromClient);
            System.out.println(r);
            
        } catch (IOException e) {
            System.out.println("Something went wrong while handling a request.");
            e.printStackTrace();
        }
    }
}
