package reflection.server;

import httpserver.HttpRequest;
import httpserver.HttpResponse;

public class SiteControllerV6 {

	public void site1(HttpRequest request, HttpResponse response) {
		response.writeBody("<h1>site1</h1>");
	}

	public void site2(HttpRequest request, HttpResponse response) {
		response.writeBody("<h1>site2</h1>");
	}

}
