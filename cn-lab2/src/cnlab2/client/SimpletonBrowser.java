package cnlab2.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cnlab2.common.Response;
import cnlab2.common.URI;

public class SimpletonBrowser {
    
    public void Browse(Response response, Client client) throws UnknownHostException, IOException, IllegalAccessException {
        
        SaveResponse(response);
        
        System.out.println("\nReading images:");
        
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
        
        List<HTTPCommand> toGet = new ArrayList<>();
        for(String res: resources) {
            if (!res.contains("//")) {
                String url = response.getUri().getHost() + "/" + res;
                toGet.add(new HTTPCommand("GET", new URI(url, response.getUri().getPort())));
            }
        }
        
        HTTPCommand[] stockArr = new HTTPCommand[toGet.size()];
        stockArr = toGet.toArray(stockArr);
        List<Response> responses = client.handle(stockArr);
        
        System.out.println("\nImages:");
        System.out.println(responses.size());
        
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
