package cnlab2.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class PutHandler extends Handler {
    
    public PutHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }
    
    @Override
    public Response handle() throws IOException {
        String localLoc = getRequest().getHeader().getUri().getLocalLocation();
        System.out.println(localLoc);
        
        // we assume only text is send.
        String content = getRequest().getContentString();
        System.out.println(content);
        
        try{
            Response resp = null;
            
            File file = new File(localLoc);

            System.out.println("Checking if file exists");
            //if file doesnt exists, then create it
            if (!file.exists()){
                System.out.println("Creating new file.");
                file.createNewFile();
                resp = getCreatedResponse();
            }
            else {
                resp = getNoContentResponse();
            }
            
            System.out.println("appending to file");
            
            //true = append file
            FileWriter fileWritter = getWriter(file);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(content);
            bufferWritter.close();

            System.out.println("Appended, returning response.");
            return resp;
        }
        catch (IOException e){
            e.printStackTrace();
            
            return getFailedResponse();
        }
    }
    
    // for easy overriding with PostHandler ;)
    protected FileWriter getWriter(File file) throws IOException {
        return new FileWriter(file,true);
    }
    
    private Response getCreatedResponse() {
        return new Response(
                getRequest().getHeader().getVersion(),
                201,
                "Created"); 
    }
    
    private Response getNoContentResponse() {
        // we return a response without content.
        // alternatively we could send a 200 with the
        // updated content, however we chose not to.
        return new Response(
                getRequest().getHeader().getVersion(),
                204,
                "No Content"); 
    }
    
    private Response getFailedResponse() {
        return new Response(
                getRequest().getHeader().getVersion(),
                400,
                "Bad Request"); 
    }
}
