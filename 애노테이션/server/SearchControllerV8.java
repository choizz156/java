package server;

import httpserver.HttpRequest;
import httpserver.HttpResponse;

public class SearchControllerV8 {

	@Mapping("/search")
	public void search(HttpRequest request, HttpResponse response) {
		String query = request.getParameter("q");
		response.writeBody("<h1>Search</h1>");
		response.writeBody("<ul>");
		response.writeBody("<li>query: " + query + "</li>");
		response.writeBody("</ul>");
	}
}
