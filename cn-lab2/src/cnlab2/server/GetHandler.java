package cnlab2.server;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class GetHandler extends Handler {
    
    public GetHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }
    
    @Override
    public Response handle() throws IOException {
        String pathStr = getRequest().getHeader().getUri().getResource();
        if (getRequest().getHeader().getUri().getResource().equals("/")) {
            pathStr = "index.html";
        }
        
        // Find path of file
        Path path = Paths.get(StupidServer.ROOT_DIR + File.separator + pathStr);
        System.out.println("Looking for file: " + path.toString());
        
        // Read file to string
        List<String> strings = Files.readAllLines(path, Charset.defaultCharset());
        StringBuilder contentBuilder = new StringBuilder();
        for (String s : strings) {
            contentBuilder.append(s);
            contentBuilder.append("\n");
        }
        String content = contentBuilder.toString();
        
        // Construct a response
        Response resp = new Response(getRequest().getHeader().getVersion(), 200, "OK", content);
        
        // Send the response
        System.out.println("Sending response:\n" + resp.toString());
        return resp;
        
    }
    
}
