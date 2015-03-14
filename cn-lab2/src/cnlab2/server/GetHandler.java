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
        // TODO check ifchanged header
        // TODO other file types
        // TODO other error codes
        
        String pathStr = getRequest().getHeader().getUri().getLocalLocation();
        
        // Find path of file
        Path path = Paths.get(pathStr);
        System.out.println("Looking for file: " + path.toString());
        
        Response resp = null;
        Date localDate = getModifiedDate(path);
        
        System.out.println("Local date: " + localDate);
        
        if (localDate != null) {
            Date remoteDate = null;
            String dateString = getRequest().getHeader().getHeaderField("If-Modified-Since");
            try {
                DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                df.setTimeZone(TimeZone.getTimeZone("GMT"));
            
                remoteDate = df.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Invalid date: " + dateString);
            }
            
            if (remoteDate != null && remoteDate.before(localDate)) {
                resp = getOKResponse(path);
            }
            else {
                resp = getNotChangedResponse();
            }
        }
        else {
            resp = getNoFileResponse();
        }
        
        // Send the response
        System.out.println("Sending response:\n" + resp.toString());
        return resp;
        
    }
    
    private Date getModifiedDate(Path path) {
        File f = path.toFile();
        if (!f.exists()) {
            return null;
        }
        
        Long lastModified = f.lastModified();
        Date lastModifiedDate = new Date(lastModified);
        return lastModifiedDate;
    }
    
    private Response getNotChangedResponse() {
        return new Response(
                getRequest().getHeader().getVersion(),
                304,
                "Not Modified",
                "text/html",
                new byte[0]);
    }
    
    private Response getOKResponse(Path path) throws IOException {
        byte[] content = Files.readAllBytes(path);
        return new Response(
                getRequest().getHeader().getVersion(),
                200,
                "OK",
                typeOfPath(path.toString()),
                content);
    }
    
    private Response getNoFileResponse() throws IOException {
        return new Response(
                getRequest().getHeader().getVersion(),
                404,
                "Not Found",
                "text/html",
                get404ResponseContent());
    }
    
    private byte[] get404ResponseContent() throws IOException {
        Path path = Paths.get("src/cnlab2/server/FourOFour.html");
        System.out.println(path.toAbsolutePath());
        return Files.readAllBytes(path);
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
