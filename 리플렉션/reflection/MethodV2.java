package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import reflection.data.BasicData;

public class MethodV2 {
	public static void main(String[] args) throws
		NoSuchMethodException,
		InvocationTargetException,
		IllegalAccessException {
		BasicData helloInstance = new BasicData();

		helloInstance.call();

		Class<? extends BasicData> helloClass = helloInstance.getClass();
		String methodName = "hello";

		Method declaredMethod = helloClass.getDeclaredMethod(methodName, String.class);
		Object returnValue = declaredMethod.invoke(helloInstance, "hi");
		System.out.println("returnValue = " + returnValue);
	}
}
