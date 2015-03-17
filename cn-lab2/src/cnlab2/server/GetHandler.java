package cnlab2.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class GetHandler extends Handler {
    
    public GetHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }
    
    @Override
    public Response handle() throws IOException {
        // Find the local location of the requested file
        String pathStr = getRequest().getHeader().getUri().getLocalLocation();
        
        // Find path of file
        Path path = Paths.get(pathStr);
        System.out.println("Looking for file: " + path.toString());
        
        Response resp = null;
        // Check when the wanted file was last modified (return null if the file
        // doesn't exist)
        Date localDate = getModifiedDate(path);
        
        System.out.println("Local date: " + localDate);
        
        // If the file exists
        if (localDate != null) {
            // Check if an 'If-Modified-Since' header is present in the proper
            // format
            Date remoteDate = null;
            if (getRequest().getHeader().getHeaderMap().containsKey("If-Modified-Since")) {
                String dateString = getRequest().getHeader().getHeaderField("If-Modified-Since");
                try {
                    DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                    df.setTimeZone(TimeZone.getTimeZone("GMT"));
                    
                    remoteDate = df.parse(dateString);
                } catch (ParseException e) {
                    System.out.println("Invalid date: " + dateString);
                }
            }
            
            // Check if the remote date is before out local
            if (remoteDate != null && remoteDate.after(localDate)) {
                // if so send the updated file
                resp = getNotChangedResponse();
            } else {
                // otherwise send a response saying nothing changed.
                resp = getOKResponse(path);
            }
        } else {
            // If the requested file is not found
            // send a 'Not Found' response.
            resp = getNoFileResponse();
        }
        
        // Return the response
        System.out.println("Sending response:\n" + resp.toString());
        return resp;
        
    }
    
    private Date getModifiedDate(Path path) {
        File f = path.toFile();
        // if no file is found return null
        if (!f.exists()) { return null; }
        
        // check the last modified date
        Long lastModified = f.lastModified();
        Date lastModifiedDate = new Date(lastModified);
        return lastModifiedDate;
    }
    
    private Response getNotChangedResponse() {
        return new Response(getRequest().getHeader().getVersion(), 304, "Not Modified");
    }
    
    private Response getOKResponse(Path path) throws IOException {
        byte[] content = Files.readAllBytes(path);
        return new Response(getRequest().getHeader().getVersion(), 200, "OK", typeOfPath(path.toString()), content);
    }
    
    private Response getNoFileResponse() throws IOException {
        // returns a 404 page as content
        return new Response(getRequest().getHeader().getVersion(), 404, "Not Found", "text/html", get404ResponseContent());
    }
    
    private byte[] get404ResponseContent() throws IOException {
        Path path = Paths.get("src/cnlab2/server/FourOFour.html");
        System.out.println(path.toAbsolutePath());
        return Files.readAllBytes(path);
    }
    
    public String typeOfPath(String path) {
        // find the extension
        int i = path.lastIndexOf(".");
        String extension = path.substring(i + 1);
        String type;
        
        // check the type of the extension to known types.
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
        
        // return the formatted type string.
        return type + "/" + extension;
    }
    
}
