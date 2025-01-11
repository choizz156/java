package reflection;

import java.lang.reflect.Constructor;

public class ContructionV1 {
	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> aClass = Class.forName("reflection.data.BasicData");

		System.out.println("=========생성자");

		Constructor<?>[] constructors = aClass.getConstructors();
		for (Constructor<?> constructor : constructors) {
			System.out.println(constructor);
		}

		System.out.println("declaredConstructor");
		Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
		for (Constructor<?> declaredConstructor : declaredConstructors) {
			System.out.println(declaredConstructor);
		}


	}
}
