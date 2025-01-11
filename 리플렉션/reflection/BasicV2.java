package reflection;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import reflection.data.BasicData;

public class BasicV2 {

	public static void main(String[] args) {
		Class<BasicData> basicDataClass = BasicData.class;
		System.out.println("basicDataClass.getName() = " + basicDataClass.getName());
		System.out.println("basicDataClass.getSimpleName() = " + basicDataClass.getSimpleName());
		System.out.println("basicDataClass.getPackage() = " + basicDataClass.getPackage());
		System.out.println("basicDataClass.getSuperclass() = " + basicDataClass.getSuperclass());
		System.out.println("basicDataClass.getInterfaces() = " + Arrays.toString(basicDataClass.getInterfaces()));
		System.out.println("basicDataClass.isInterface() = " + basicDataClass.isInterface());
		System.out.println("basicDataClass.isAnnotation() = " + basicDataClass.isAnnotation());
		System.out.println("basicDataClass.isEnum() = " + basicDataClass.isEnum());
		System.out.println("basicDataClass.getModifiers() = " + basicDataClass.getModifiers());
		System.out.println(
			"Modifier.isPublic(basicDataClass.getModifiers()) = " + Modifier.isPublic(basicDataClass.getModifiers()));
		System.out.println(
			"Modifier.toString(basicDataClass.getModifiers()) = " + Modifier.toString(basicDataClass.getModifiers()));
	}
}
