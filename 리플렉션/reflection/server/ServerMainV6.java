package reflection.server;

import java.util.List;

import httpserver.ServletManager;
import httpserver.servlet.DiscardServlet;
import httpserver.servlet.servlet.HomeServlet;

public class ServerMainV6 {
	private static final int PORT = 12345;

	public static void main(String[] args) {

		List<Object> controllers = List.of(new SiteControllerV6(), new SearchControllerV6());

		ReflectionServlet reflectionServlet = new ReflectionServlet(controllers);

		ServletManager servletManager = new ServletManager();
		servletManager.setDefaultServlet(reflectionServlet);

		servletManager.add("/", new HomeServlet());
		servletManager.add("/favicon.ico", new DiscardServlet());


	}
}
