package server;

import java.lang.reflect.Method;

import httpserver.HttpRequest;
import httpserver.HttpResponse;

public class ControllerMethod {
	private final Object controller;
	public final Method method;

	public ControllerMethod(Object controller, Method method) {
		this.controller = controller;
		this.method = method;
	}

	public void invoke(HttpRequest request, HttpResponse response) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] args = new Object[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i] == HttpRequest.class) {
				args[i] = request;
			} else if (parameterTypes[i] == HttpResponse.class) {
				args[i] = response;
			} else {
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
