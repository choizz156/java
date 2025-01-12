package reflection.server;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

	private final PrintWriter writer;
	private int statusCode = 200;
	private final StringBuilder bodyBuilder = new StringBuilder();
	private String contentType = "text/html; charset=utf-8";

	public HttpResponse(PrintWriter writer) {
		this.writer = writer;
	}

	public void writeBody(String body) {
		bodyBuilder.append(body);
	}

	public void flush() {
		int contentLength = bodyBuilder.toString().getBytes(StandardCharsets.UTF_8).length;
		writer.println("HTTP/1.1 " + statusCode + " " + getReasonPhrase(statusCode));
		writer.println("Content-Type: " + contentType);
		writer.println("Content-Length: " + contentLength);
		writer.println();
		writer.println(bodyBuilder);
		writer.flush();
	}

	private String getReasonPhrase(int statusCode) {
		switch (statusCode) {
			case 200:
				return "OK";
			case 404:
				return "Not Found";
			case 500:
				return "Internal Server Error";
			default:
				return "Unknown site";
		}
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public StringBuilder getBodyBuilder() {
		return bodyBuilder;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
