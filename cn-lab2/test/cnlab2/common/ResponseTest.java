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

	@BeforeClass
	public static void setUp() {
		
	}

	@Test
	public void test() {
		Response r = new Response(testResponseStr);
		assertEquals(testResponseHeaderStr,r.getHeader());
	}

}
