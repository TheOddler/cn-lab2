package cnlab2.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cnlab2.common.Request;
import cnlab2.common.SmartSocket;

public class PostHandler extends PutHandler {
    
    /**
     * The PostHandler is the same as a PutHandler, but overrides rather than appending to a file.
     * @param socket
     * @param request
     */
    public PostHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }
    
    /**
     * Get the FileWriter for the post handler.
     * This writer does not append, but overrides.
     */
    @Override
    protected FileWriter getWriter(File file) throws IOException {
        return new FileWriter(file, false);
    }
    
}
