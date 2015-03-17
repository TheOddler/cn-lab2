package cnlab2.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cnlab2.common.Response;
import cnlab2.common.SocketClosedException;
import cnlab2.common.URI;

/**
 * A simple browser that handler parsing html pages and downloaden extra content
 * needed for is.
 *
 */
public class SimpleBrowser {
    
    private Response response;
    private Client client;
    
    public SimpleBrowser(Response response, Client client) {
        this.response = response;
        this.client = client;
    }
    
    /**
     * Let the browser do it's thing (namely browse ;) )
     * 
     * @param response
     *            The response, we assume it's an html page.
     * @param client
     * @throws UnknownHostException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws SocketClosedException
     */
    public void Browse() throws UnknownHostException, IOException, IllegalAccessException, SocketClosedException {
        // Save the current response to the drive
        saveResponse(this.response);
        int status = this.response.getHeader().getStatus();
        if (status == 200) { // okay
            if (response.getHeader().getHeaderField("Content-Type").contains("text/html")) {
                List<String> resources = new ArrayList<>();
                
                readLinkTags(resources);
                readImages(resources);
                readScriptTags(resources);
                
                fetchResources(resources);
            }
        }
        if (status == 301 || status == 302) {// Moved
            if (this.response.getHeader().getHeaderMap().containsKey("Location")) {
                
                String location = this.response.getHeader().getHeaderField("Location");
                URI uri = new URI(location);
                
                // Handler the command given in the arguments
                HTTPCommand comm = new HTTPCommand("GET", uri);
                Response response = client.handle(comm);
                
                // If the argument was a GET, and we got a html page, then let
                // our
                // simple browser handle the rest.
                SimpleBrowser browser = new SimpleBrowser(response, client);
                browser.Browse();
            }
        }
    }
    
    private void readLinkTags(List<String> resources) throws IllegalAccessException, IOException, SocketClosedException {
        System.out.println("Reading link tags...");
        
        String cssRegex = "<link[^>]+href\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        Pattern p = Pattern.compile(cssRegex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(new String(response.getContent()));
        while (m.find()) {
            resources.add(m.group(1));
        }
    }
    
    private void readScriptTags(List<String> resources) throws IllegalAccessException, IOException, SocketClosedException {
        System.out.println("Reading script tags...");
        
        String cssRegex = "<script[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        Pattern p = Pattern.compile(cssRegex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(new String(response.getContent()));
        while (m.find()) {
            resources.add(m.group(1));
        }
    }
    
    private void readImages(List<String> resources) throws IllegalAccessException, IOException, IllegalAccessException, SocketClosedException {
        // Read in the images from this html page
        System.out.println("Reading images...");
        
        // Find all img tags and get their resources
        String imgRegex = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        Pattern p = Pattern.compile(imgRegex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(new String(response.getContent()));
        while (m.find()) {
            resources.add(m.group(1));
        }
    }
    
    public void fetchResources(List<String> resources) throws IllegalAccessException, IOException, IllegalAccessException, SocketClosedException {
        System.out.println("\nGetting resources:");
        
        // Create commands to get each image
        List<HTTPCommand> toGet = new ArrayList<>();
        for (String res : resources) {
            // Only fetch the local resources
            if (!res.contains("//")) {
                String url = response.getUri().getHost() + "/" + res;
                toGet.add(new HTTPCommand("GET", new URI(url, response.getUri().getPort())));
            }
        }
        
        System.out.println("Checked " + resources.size() + " resources, of which " + toGet.size() + " were local.");
        
        // Let the client get all the images
        List<Response> responses = client.handle(toGet);
        
        System.out.println("Received resources: " + responses.size());
        
        System.out.println("Saving them to disk...");
        // Save all the images to disk
        for (Response resp : responses) {
            saveResponse(resp);
        }
    }
    
    private void saveResponse(Response response) throws IOException {
        File file = new File(response.getUri().getLocalLocation());
        new File(file.getParent()).mkdirs();
        
        FileOutputStream output = new FileOutputStream(file);
        output.write(response.getContent());
        output.close();
    }
    
}
