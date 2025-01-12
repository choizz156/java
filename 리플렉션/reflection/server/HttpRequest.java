package reflection.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class HttpRequest {
	private String method;
	private String path;
	private final Map<String, String> queryParameters = new HashMap<>();
	private final Map<String, String> headers = new HashMap<>();

	public HttpRequest(BufferedReader reader) throws IOException {
		parseRequestLine(reader);
		parseHeaders(reader);
	}

	private void parseRequestLine(BufferedReader reader) throws IOException {
		String requestLine = reader.readLine();
		if (requestLine == null) {
			throw new IOException("EOF: no request line");
		}

		String[] parts = requestLine.split(" ");
		if (parts.length != 3) {
			throw new IOException("EOF: invalid request line");
		}

		method = parts[0];
		String[] pathParts = parts[1].split("\\?");
		path = pathParts[0];

		if (pathParts.length > 1) {
			parseQueryParameters(pathParts[1]);
		}
	}

	private void parseQueryParameters(String queryString) {
		for (String param : queryString.split("&")) {
			String[] keyValue = param.split("=");
			String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
			String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1],StandardCharsets.UTF_8) : "";
			queryParameters.put(key, value);
		}
	}

	private void parseHeaders(BufferedReader reader) throws IOException {
		String line;
		while (!(line = reader.readLine()).isEmpty()) {
			String[] headParts = line.split(":");
			headers.put(headParts[0].trim(), headParts[1].trim());
		}
	}
}
