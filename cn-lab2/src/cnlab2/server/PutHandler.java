package cnlab2.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cnlab2.common.Request;
import cnlab2.common.Response;
import cnlab2.common.SmartSocket;

public class PutHandler extends Handler {
    
    /**
     * Handler for a PUT request to the server
     * @param socket
     * @param request
     */
    public PutHandler(SmartSocket socket, Request request) {
        super(socket, request);
    }
    
    @Override
    public Response handle() throws IOException {
        // Find where the request wants to put the data
        String localLoc = getRequest().getHeader().getUri().getLocalLocation();
        System.out.println(localLoc);
        
        // we assume only text is send.
        String content = getRequest().getContentString();
        System.out.println(content);
        
        try{
            Response resp = null;
            
            // Create a file object for the wanted location
            File file = new File(localLoc);

            // if file doesnt exists, then create it
            // also create the proper response
            if (!file.exists()){
                file.createNewFile();
                resp = getCreatedResponse();
            }
            else {
                resp = getNoContentResponse();
            }
            
            // Write to the file
            FileWriter fileWritter = getWriter(file); //gets the writer to use.
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            // write the content
            bufferWritter.write(content);
            bufferWritter.close();
            
            // return the response to be send.
            return resp;
        }
        catch (IOException e){
            e.printStackTrace();
            
            // if something went wrong while writing, return a
            // failed response
            return getFailedResponse();
        }
    }
    
    /**
     * Returns the writer to be used by this handler.
     * @param file File to write to
     * @return The writer, one that appends to the existing file.
     * @throws IOException
     */
    protected FileWriter getWriter(File file) throws IOException {
        // True = append
        // PUT appends, so we return a writer that appends to the file if one exists.
        return new FileWriter(file,true);
    }
    
    /**
     * Get a response for when a new file was created
     * @return
     */
    private Response getCreatedResponse() {
        return new Response(
                getRequest().getHeader().getVersion(),
                201,
                "Created"); 
    }
    
    /**
     * Get the response for when writing was successful
     * We don't return the new content, just a simple response saying
     * it was successful
     * @return
     */
    private Response getNoContentResponse() {
        // we return a response without content.
        // alternatively we could send a 200 with the
        // updated content, however we chose not to.
        return new Response(
                getRequest().getHeader().getVersion(),
                204,
                "No Content"); 
    }
    
    /**
     * Get the response for when something went wrong.
     * @return
     */
    private Response getFailedResponse() {
        return new Response(
                getRequest().getHeader().getVersion(),
                400,
                "Bad Request"); 
    }
}
