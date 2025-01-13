package server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import httpserver.HttpRequest;
import httpserver.HttpResponse;
import httpserver.HttpServlet;
import httpserver.PageNotFoundException;

public class AnnotationServletV3 implements HttpServlet {

	private final Map<String, ControllerMethod> pathMap;

	public AnnotationServletV3(List<Object> controllers) {
		this.pathMap = new HashMap<>();
		initalizePathMap(controllers);
	}

	private void initalizePathMap(List<Object> controllers) {
		for (Object controller : controllers) {
			Method[] declaredMethods = controller.getClass().getDeclaredMethods();
			for (Method method : declaredMethods) {
				if (method.isAnnotationPresent(Mapping.class)) {
					Mapping mapping = method.getAnnotation(Mapping.class);
					String path = mapping.value();

					if(pathMap.containsKey(path)) {
						ControllerMethod controllerMethod = pathMap.get(path);
						throw new IllegalArgumentException("경로 중복 등록, path : " + path + ", method : " + method + ", 이미 등록된 메서드= " + controllerMethod.method);
					}

					pathMap.put(path,new ControllerMethod(controller, method));
				}
			}
		}
	}

	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		String path = request.getPath();
		ControllerMethod controllerMethod = pathMap.get(path);

		if(controllerMethod == null) {
			throw new PageNotFoundException("request = " + request);
		}

		controllerMethod.invoke(request, response);
	}


}
