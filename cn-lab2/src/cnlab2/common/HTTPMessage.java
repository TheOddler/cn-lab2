package cnlab2.common;

import java.io.IOException;

public class HTTPMessage {
    private String content;
    private HTTPHeader header;

    protected void readContent(SmartSocket in) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        int totalRead = 0;
        String line;
        if (getHeader().getHeaderMap().containsKey("Content-Length")) {
            int length = Integer.parseInt(getHeader().getHeaderMap().get("Content-Length"));
            while (totalRead < length) {
                line = in.readLine();
                if (line == null) break;

                contentBuilder.append(line);

                totalRead += line.length();
                totalRead += 1; // the newline character
            }
        }

        if (contentBuilder.toString().equals("")) contentBuilder.append("\n");
        setContent(contentBuilder.toString());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HTTPHeader getHeader() {
        return header;
    }

    public void setHeader(HTTPHeader header) {
        this.header = header;
    }

}
