package reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import reflection.data.Calculator;

public class MethodV3 {
	public static void main(String[] args) throws
		NoSuchMethodException,
		InvocationTargetException,
		IllegalAccessException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("호출메서드 : ");
		String methodName = scanner.nextLine();

		System.out.print("숫자 1: ");
		int num1 = scanner.nextInt();
		System.out.print("숫2ㅏ 2: ");
		int num2 = scanner.nextInt();

		Calculator calculator = new Calculator();

		Class<? extends Calculator> calculatorClass = Calculator.class;
		Method method = calculatorClass.getMethod(methodName, int.class, int.class);
		Object returnValue = method.invoke(calculator, num1, num2);
		System.out.println("returnValue = " + returnValue);

	}
}
