package cnlab2.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class GetHandler extends Handler {
    
    public GetHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }
    
    @Override
    public Response handle() throws IOException {
        // TODO check ifchanged header
        // TODO other file types
        // TODO other error codes
        
        String pathStr = getRequest().getHeader().getUri().getLocalLocation();
        
        // Find path of file
        Path path = Paths.get(pathStr);
        System.out.println("Looking for file: " + path.toString());
        
        // Read file to string
        // TODO always just read bytes
        byte[] content;
        if (Files.exists(path)) {
            content = Files.readAllBytes(path);
        }
        else {
            content = new byte[0];
        }
        
        // Construct a response
        Response resp = new Response(
                getRequest().getHeader().getVersion(),
                200,
                "OK",
                typeOfPath(path.toString()),
                content);
        
        // Send the response
        System.out.println("Sending response:\n" + resp.toString());
        return resp;
        
    }
    
    public String typeOfPath(String path) {
        int i = path.lastIndexOf(".");
        String extension = path.substring(i+1);
        String type;
        
        switch (extension) {
            case "gif":
            case "png":
            case "jpg":
                type = "image";
                break;
            case "html":
            case "txt":
            case "md":
            case "css":
            case "js":
                type = "text";
                break;
            default:
                type = "unknown";
                break;
        }
        
        return type + "/" + extension;
    }
    
}
