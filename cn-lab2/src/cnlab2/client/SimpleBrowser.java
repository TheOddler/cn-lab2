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
 * A simple browser that handler parsing html pages and downloaden extra content neede for is.
 *
 */
public class SimpleBrowser {
    
    /**
     * Let the browser do it's thing (namely browse ;) )
     * @param response The response, we assume it's a html page.
     * @param client
     * @throws UnknownHostException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws SocketClosedException
     */
    public void Browse(Response response, Client client) throws UnknownHostException, IOException, IllegalAccessException, SocketClosedException {
        // Save the current response to the drive
        SaveResponse(response);
        
        // Read in the images from this html page
        System.out.println("\nReading images:");
        
        // Find all img tags and get their resources
        List<String> resources = new ArrayList<>();
        if (response.getHeader().getHeaderField("Content-Type").contains("text/html")) {
            String imgRegex = "<[i|I][m|M][g|G][^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
            Pattern p = Pattern.compile(imgRegex);
            Matcher m = p.matcher(new String(response.getContent()));
            while (m.find()) {
                resources.add(m.group(1));
            }
        }
        
        System.out.println("\nGetting images:");
        
        // Create commands to get each image
        List<HTTPCommand> toGet = new ArrayList<>();
        for(String res: resources) {
            if (!res.contains("//")) {
                String url = response.getUri().getHost() + "/" + res;
                toGet.add(new HTTPCommand("GET", new URI(url, response.getUri().getPort())));
            }
        }
        
        // Let the client get all the images
        List<Response> responses = client.handle(toGet);
        
        System.out.print("\nImage-count: ");
        System.out.println(responses.size());
        
        // Save all the images to disk
        for(Response resp: responses) {
            SaveResponse(resp);
        }
    }
    
    private void SaveResponse(Response response) throws IOException {
        File file = new File(response.getUri().getLocalLocation());
        new File(file.getParent()).mkdirs();
        
        FileOutputStream output = new FileOutputStream(file);
        output.write(response.getContent());
        output.close();
    }
    
}
