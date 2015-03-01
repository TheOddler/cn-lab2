package cnlab2.client;

import cnlab2.common.URI;

public class HTTPCommand {
    private String command;
    private URI uri;
    
    public HTTPCommand(String command, URI uri) {
        this.setCommand(command);
        this.setUri(uri);
    }

    public String getCommand() {
        return command;
    }

    private void setCommand(String command) {
        this.command = command;
    }

    public URI getUri() {
        return uri;
    }

    private void setUri(URI uri) {
        this.uri = uri;
    }
    
    
}
