package cnlab2.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ResponseTest {

	private static final String emptyResponse = "HTTP/1.0 200 OK\n\n\n\n";
	private static final String simpleResponse = "HTTP/1.1 200 OK\n\n<html>hi</html>\n\n";
	private static final String simpleLongResponse = "HTTP/1.1 200 OK\n\n<html>hi\ni\nam\nbatman\n</html>\n\n";
	private static final String shortHeaderResponse = "HTTP/1.0 200 OK\nContent-Type: text/html\n\n<html>hi</html>\n\n";
	private static final String longHeaderResponse = "HTTP/1.0 200 OK\nContent-Type: text/html\nContent-Length: 1354\n\n<html>hi</html>\n\n";
	private static final String longResponse = "HTTP/1.0 200 OK\nContent-Type: text/html\nContent-Length: 13\n\n<html>hi\ni\nam\nbatman\n</html>\n\n";

	@Test
	public void emptyResponseParseTest() {
		Response r = new Response(emptyResponse);
		assertEquals("HTTP/1.0", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.0 200 OK\n", r.getHeader());
		assertEquals("\n", r.getContent());
		assertEquals(emptyResponse, r.toString());
	}

	@Test
	public void simpleResponseParseTest() {
		Response r = new Response(simpleResponse);
		assertEquals("HTTP/1.1", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.1 200 OK\n", r.getHeader());
		assertEquals("<html>hi</html>\n", r.getContent());
		assertEquals(simpleResponse, r.toString());
	}

	@Test
	public void simpleLongResponseParseTest() {
		Response r = new Response(simpleLongResponse);
		assertEquals("HTTP/1.1", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals("HTTP/1.1 200 OK\n", r.getHeader());
		assertEquals("<html>hi\ni\nam\nbatman\n</html>\n", r.getContent());
		assertEquals(simpleLongResponse, r.toString());
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
		assertEquals("<html>hi</html>\n", r.getContent());
		assertEquals(shortHeaderResponse, r.toString());
	}

	@Test
	public void longHeaderResponseParseTest() {
		Response r = new Response(longHeaderResponse);
		assertEquals("HTTP/1.0", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals(
				"HTTP/1.0 200 OK\nContent-Type: text/html\nContent-Length: 1354\n",
				r.getHeader());
		assertTrue(r.getHeaderMap().containsKey("Content-Type"));
		assertEquals("text/html", r.getHeaderMap().get("Content-Type"));
		assertTrue(r.getHeaderMap().containsKey("Content-Length"));
		assertEquals("1354", r.getHeaderMap().get("Content-Length"));
		assertEquals("<html>hi</html>\n", r.getContent());
		assertEquals(longHeaderResponse, r.toString());
	}

	@Test
	public void longResponseParseTest() {
		Response r = new Response(longResponse);
		assertEquals("HTTP/1.0", r.getVersion());
		assertEquals(200, r.getStatus());
		assertEquals("OK", r.getMessage());
		assertEquals(
				"HTTP/1.0 200 OK\nContent-Type: text/html\nContent-Length: 13\n",
				r.getHeader());
		assertTrue(r.getHeaderMap().containsKey("Content-Type"));
		assertEquals("text/html", r.getHeaderMap().get("Content-Type"));
		assertTrue(r.getHeaderMap().containsKey("Content-Length"));
		assertEquals("13", r.getHeaderMap().get("Content-Length"));
		assertEquals("<html>hi\ni\nam\nbatman\n</html>\n", r.getContent());
		assertEquals(longResponse, r.toString());
	}
}
