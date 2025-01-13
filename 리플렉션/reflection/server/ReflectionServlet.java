package reflection.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import httpserver.HttpRequest;
import httpserver.HttpResponse;
import httpserver.HttpServlet;
import httpserver.PageNotFoundException;

public class ReflectionServlet implements HttpServlet {
	private final List<Object> controllers;

	public ReflectionServlet(List<Object> controllers) {
		this.controllers = controllers;
	}

	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String path = request.getPath();
		for (Object controller : controllers) {
			Class<?> aClass = controller.getClass();
			Method[] methods = aClass.getDeclaredMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (path.equals("/" + methodName)) {
					invoke(controller, method, request, response);
					return;
				}
			}
		}
		throw new PageNotFoundException("request=" + path);
	}

	private void invoke(Object controller, Method method, HttpRequest request, HttpResponse response) {
		try {
			method.invoke(controller, request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
