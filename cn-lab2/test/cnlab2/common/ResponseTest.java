package cnlab2.common;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ResponseTest {
	
	private static final String testResponseStr = "HTTP/1.0 200 OK\n"
			+ "Date: Fri, 31 Dec 1999 23:59:59 GMT\n"
			+ "Content-Type: text/html\n"
			+ "Content-Length: 1354\n"
			+ "\n"
			+ "<html>\n"
			+ "<body>\n"
		    + "<h1>Happy New Millennium!</h1>\n"
		    + "(more file contents)\n"
		    + "</body>\n"
		    + "</html>\n";
	private static final String testResponseHeaderStr ="HTTP/1.0 200 OK\n"
			+ "Date: Fri, 31 Dec 1999 23:59:59 GMT\n"
			+ "Content-Type: text/html\n"
			+ "Content-Length: 1354\n";
	
	private static final String emptyResponse = "HTTP/1.0 200 OK\n\n";
	private static final String simpleResponse = "HTTP/1.1 200 OK\n\n<html>hi</html>";
	
	
	@BeforeClass
	public static void setUp() {
		
	}
	
	@Test
	public void emptyResponseParseTest(){
		Response r = new Response(emptyResponse);
		assertEquals("HTTP/1.0", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.0 200 OK",r.getHeader());
		assertEquals("",r.getContent());
	}
	
	@Test
	public void simpleResponseParseTest(){
		Response r = new Response(simpleResponse);
		assertEquals("HTTP/1.1", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.1 200 OK",r.getHeader());
		assertEquals("<html>hi</html>",r.getContent());
	}

	public void test() {
		Response r = new Response(testResponseStr);
		assertEquals(testResponseHeaderStr,r.getHeader());
	}

}
