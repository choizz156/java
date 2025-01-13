package server;

import httpserver.HttpRequest;
import httpserver.HttpResponse;

public class AnnotationController {

	@Mapping("/")
	public void home(HttpRequest req, HttpResponse resp) {

	}

	public void site1(HttpRequest req, HttpResponse resp) {}

	public void site2(HttpRequest req, HttpResponse resp) {}
}
