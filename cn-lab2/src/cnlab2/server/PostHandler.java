package cnlab2.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cnlab2.common.Request;
import cnlab2.common.SmartSocket;

public class PostHandler extends PutHandler {
    
    public PostHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }
    
    @Override
    protected FileWriter getWriter(File file) throws IOException {
        return new FileWriter(file, false);
    }
    
}
