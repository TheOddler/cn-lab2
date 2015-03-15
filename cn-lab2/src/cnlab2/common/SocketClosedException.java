package cnlab2.common;

/**
 * Exception to be send when a socket has been closed when trying to read from it.
 */
public class SocketClosedException extends Exception {
    //Parameterless Constructor
    public SocketClosedException() {}

    //Constructor that accepts a message
    public SocketClosedException(String message) {
       super(message);
    }
}
