package server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import httpserver.HttpRequest;
import httpserver.HttpResponse;
import httpserver.HttpServlet;
import httpserver.PageNotFoundException;

public class AnnotationServletV2 implements HttpServlet {

	private final List<Object> controllers;

	public AnnotationServletV2(List<Object> controllers) {
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

		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] args = new Object[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i] == HttpRequest.class) {
				args[i] = request;
			}else if (parameterTypes[i] == HttpResponse.class) {
				args[i] = response;
			}else {
				throw new IllegalArgumentException("unsupported parameter type: " + parameterTypes[i]);
			}
		}

		try {
			method.invoke(controller, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
