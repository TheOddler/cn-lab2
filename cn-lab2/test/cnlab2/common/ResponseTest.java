package cnlab2.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ResponseTest {

	private static final String testResponseStr = "HTTP/1.0 200 OK\n"
			+ "Date: Fri, 31 Dec 1999 23:59:59 GMT\n"
			+ "Content-Type: text/html\n" + "Content-Length: 1354\n" + "\n"
			+ "<html>\n" + "<body>\n" + "<h1>Happy New Millennium!</h1>\n"
			+ "(more file contents)\n" + "</body>\n" + "</html>\n";
	private static final String testResponseHeaderStr = "HTTP/1.0 200 OK\n"
			+ "Date: Fri, 31 Dec 1999 23:59:59 GMT\n"
			+ "Content-Type: text/html\n" + "Content-Length: 1354\n";

	private static final String emptyResponse = "HTTP/1.0 200 OK\n\n";
	private static final String simpleResponse = "HTTP/1.1 200 OK\n\n<html>hi</html>";
	private static final String longResponse = "HTTP/1.1 200 OK\n\n<html>hi\ni\nam\nbatman\n</html>";
	private static final String shortHeaderResponse = "HTTP/1.0 200 OK\nContent-Type: text/html\n\n<html>hi</html>";
	private static final String longHeaderResponse = "HTTP/1.0 200 OK\nContent-Type: text/html\nContent-Length: 1354\n\n<html>hi</html>";

	@BeforeClass
	public static void setUp() {

	}

	@Test
	public void emptyResponseParseTest() {
		Response r = new Response(emptyResponse);
		assertEquals("HTTP/1.0", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.0 200 OK\n", r.getHeader());
		assertEquals("", r.getContent());
	}

	@Test
	public void simpleResponseParseTest() {
		Response r = new Response(simpleResponse);
		assertEquals("HTTP/1.1", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.1 200 OK\n", r.getHeader());
		assertEquals("<html>hi</html>", r.getContent());
	}

	@Test
	public void longResponseParseTest() {
		Response r = new Response(longResponse);
		assertEquals("HTTP/1.1", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.1 200 OK\n", r.getHeader());
		assertEquals("<html>hi\ni\nam\nbatman\n</html>", r.getContent());

	}

	@Test
	public void shortHeaderResponseParseTest() {
		Response r = new Response(shortHeaderResponse);
		assertEquals("HTTP/1.0", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.0 200 OK\nContent-Type: text/html\n",
				r.getHeader());
		assertTrue(r.getHeaderMap().containsKey("Content-Type"));
		assertEquals("text/html", r.getHeaderMap().get("Content-Type"));
		assertEquals("<html>hi</html>", r.getContent());

	}

	@Test
	public void longHeaderResponseParseTest() {
		Response r = new Response(longHeaderResponse);
		assertEquals("HTTP/1.0", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.0 200 OK\nContent-Type: text/html\nContent-Length: 1354\n",
				r.getHeader());
		assertTrue(r.getHeaderMap().containsKey("Content-Type"));
		assertEquals("text/html", r.getHeaderMap().get("Content-Type"));
		assertTrue(r.getHeaderMap().containsKey("Content-Length"));
		assertEquals("1354", r.getHeaderMap().get("Content-Length"));
		assertEquals("<html>hi</html>", r.getContent());
	}

	public void test() {
		Response r = new Response(testResponseStr);
		assertEquals(testResponseHeaderStr, r.getHeader());
	}

}
