package annotation;

import java.lang.reflect.Method;

public class TestControllerMain {

	public static void main(String[] args) {
		TestController testController = new TestController();
		Class<? extends TestController> aClass = testController.getClass();
		for (Method declaredMethod : aClass.getDeclaredMethods()) {
			BasicMapping annotation = declaredMethod.getAnnotation(BasicMapping.class);
			if (annotation != null) {
				System.out.println("annotation = " + annotation + "||||" + declaredMethod.getName());
			}
		}
	}
}
