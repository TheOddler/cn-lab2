package cnlab2.common;

public class Response {
	private final String header;
	private final String content;
	
	public Response(String response){
		content = response;
		header = null;
	}

	public String getHeader() {
		return header;
	}

	public String getContent() {
		return content;
	}
}
