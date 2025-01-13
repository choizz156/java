package httpserver.servlet.servlet;

import httpserver.HttpRequest;
import httpserver.HttpResponse;
import httpserver.HttpServlet;

public class SearchServlet implements HttpServlet {
	@Override
	public void service(HttpRequest request, HttpResponse response) {
		String query = request.getParameter("q");
		response.writeBody("<h1>Search</h1>");
		response.writeBody("<ul>");
		response.writeBody("<li>query: " + query + "</li>");
		response.writeBody("</ul>");
	}
}
