package cnlab2.common;

public class SocketClosedException extends Exception {
    //Parameterless Constructor
    public SocketClosedException() {}

    //Constructor that accepts a message
    public SocketClosedException(String message) {
       super(message);
    }
}
