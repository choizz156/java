package server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import httpserver.HttpRequest;
import httpserver.HttpResponse;
import httpserver.HttpServlet;
import httpserver.PageNotFoundException;

public class AnnotationServletV1 implements HttpServlet {

	private final List<Object> controllers;

	public AnnotationServletV1(List<Object> controllers) {
		this.controllers = controllers;
	}

	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String path = request.getPath();

		for (Object controller : controllers) {
			Method[] declaredMethods = controller.getClass().getDeclaredMethods();
			for (Method method : declaredMethods) {
				if (method.isAnnotationPresent(Mapping.class)) {
					Mapping mapping = method.getAnnotation(Mapping.class);
					String value = mapping.value();
					if (value.equals(path)) {
						invoke(controller, method, request, response);
						return;
					}
				}
			}
		}

		throw new PageNotFoundException("request = " + request);
	}

	private void invoke(Object controller, Method method, HttpRequest request, HttpResponse response) {
		try {
			method.invoke(controller, request, response);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
