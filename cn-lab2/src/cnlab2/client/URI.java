package cnlab2.client;

public class URI {
	private String protocol;
	private String host;
	private String resource;

	public URI(String uriStr) throws IllegalAccessException {
		if (uriStr == null) {
			throw new IllegalAccessException("WTF");
		}
		int index1 = uriStr.indexOf("://");
		int index2 = uriStr.indexOf("/", index1 + "://".length());
		index2 = index2 != -1 ? index2 : uriStr.length();
		setProtocol(uriStr.substring(0, index1));
		setHost(uriStr.substring(index1 + "://".length(), index2));
		setResource(uriStr.substring(index2));
	}

	@Override
	public String toString() {
		return "URI [protocol=" + getProtocol() + ", host=" + getHost()
				+ ", resource=" + getResource() + "]";
	}

	public static void main(String[] args) throws IllegalAccessException {
		System.out.println(new URI("http://www.google.com/index.html"));

	}

	public String getResource() {
		return resource;
	}

	private void setResource(String resource) {
		if (resource.isEmpty()) {
			this.resource = DEFAULT_RESOURCE;
		} else {
			this.resource = resource;
		}
	}

	public String getHost() {
		return host;
	}

	private void setHost(String host) {
		this.host = host;
	}

	public String getProtocol() {
		return protocol;
	}

	private void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	private static final String DEFAULT_RESOURCE = "/index.html";
}
