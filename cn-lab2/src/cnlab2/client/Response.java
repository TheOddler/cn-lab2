package cnlab2.client;

public class Response {
	private final String header;
	private final String content;
	
	public Response(String header, String content){
		this.header = header;
		this.content = content;
	}

	public String getHeader() {
		return header;
	}

	public String getContent() {
		return content;
	}
}
